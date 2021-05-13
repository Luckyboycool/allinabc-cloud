package com.allinabc.cloud.org.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.alibaba.fastjson.JSONObject;
import com.allinabc.cloud.admin.pojo.vo.EmployeeDetailVO;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.mapper.DepartmentMapper;
import com.allinabc.cloud.org.mapper.EmployeeMapper;
import com.allinabc.cloud.org.pojo.po.Department;
import com.allinabc.cloud.org.pojo.po.Employee;
import com.allinabc.cloud.org.pojo.vo.DepartmentTreeVO;
import com.allinabc.cloud.org.pojo.vo.DeptBaseVO;
import com.allinabc.cloud.org.pojo.vo.EmployeeDeptVO;
import com.allinabc.cloud.org.service.DepartmentService;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author QQF
 * @date 2020/12/24 10:32
 */

@Slf4j
@Service
@Transactional(readOnly = true)
public class DepartmentServiceImpl extends MybatisCommonServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private EmployeeMapper employeeMapper;

    private static List<String> childDeptId = new ArrayList<>();


    @Override
    public List<DepartmentTreeVO> findList(String id) {
        List<DepartmentTreeVO> departmentTreeVOList = departmentMapper.findListByPid(id);
        return departmentTreeVOList;
    }

    @Override
    public MybatisCommonBaseMapper<Department> getRepository() {
        return departmentMapper;
    }


    @Override
    public List<String> findListDepartId(String pid) {
        List<Department> allDepartment = departmentMapper.findList();
        if (CollectionUtils.isEmpty(allDepartment)) {
            return Arrays.asList(pid);
        }
        List<String> list = treeDepartIdList(allDepartment, pid);
        list.add(pid);
        return list;
    }

    @Override
    public List<Tree<String>> findListByName(String name) {
        List<Department> departmentAllList = departmentMapper.findList();
        List<Department> departmentResultList = new ArrayList<>();
        List<Department> departmentResultChildList = new ArrayList<>();
        if (StringUtils.isEmpty(name)) {
            departmentResultList = departmentAllList;
        } else  {
            List<Department> departmentList = departmentMapper.findListByName(name);
            this.childParentList(departmentAllList, departmentList, departmentResultList);

            departmentResultList.addAll(departmentList);
            this.childChildList(departmentAllList, departmentList, departmentResultChildList);
            departmentResultList.addAll(departmentResultChildList);
            departmentResultList = departmentResultList.stream().distinct().collect(Collectors.toList());
        }

        List<TreeNode<String>> treeNodeList = departmentResultList.stream().map(department -> {
            TreeNode<String> node = new TreeNode<>();
            node.setId(String.valueOf(department.getId()));
            node.setName(department.getName());
            node.setParentId(department.getPid());
            Map<String, Object> map = new HashMap<>(16);
            map.put("departLevel", department.getDeptLevel());
            node.setExtra(map);
            return node;
        }).collect(Collectors.toList());

        List<Tree<String>> treeNodes = this.handleCustomTreeDisplay(treeNodeList);
        log.info("查询组织架构Tree = {}", JSONObject.toJSONString(treeNodes));
        return treeNodes;
    }

    /**
     * 查询子部门下子部门
     * @param departmentAllList
     * @param departmentChildList
     * @param childChildList
     */
    private List<Department> childChildList(List<Department> departmentAllList,
                                List<Department> departmentChildList, List<Department> childChildList) {
        List<Department> childDept = new ArrayList<>();

        for (Department department : departmentChildList) {
            for (Department all : departmentAllList) {
                if (String.valueOf(department.getId()).equals(all.getPid())) {
                    childDept.add(all);
                    childChildList.add(all);
                }
            }
        }
        for (Department department : childDept) {
            List<Department> departments = childChildList(departmentAllList, Arrays.asList(department), childChildList);
            childChildList.addAll(departments);
        }
        if (childDept.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return childDept;

    }

    @Override
    public List<DeptBaseVO> findBaseList(List<String> ids) {
        List<Department> departments = this.selectByIds(ids);
        List<DeptBaseVO> deptBaseVOList = departments.stream().map(department -> {
            DeptBaseVO deptBaseVO = new DeptBaseVO();
            deptBaseVO.setDepartId(String.valueOf(department.getId()));
            deptBaseVO.setDepartName(department.getName());
            return deptBaseVO;
        }).collect(Collectors.toList());
        return deptBaseVOList;
    }

    @Override
    public Result<List<EmployeeDetailVO>> getDeptEmployees(String code) {
        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        departmentQueryWrapper.eq("CODE", code);
        Department department = departmentMapper.selectOne(departmentQueryWrapper);
        if (null == department) {
            return Result.success(Collections.EMPTY_LIST, "查询成功");
        }

        List<String> deptIds = departmentMapper.selectChild(String.valueOf(department.getId()));
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.in("DEPT_ID", deptIds);
        List<Employee> employees = employeeMapper.selectList(wrapper);
        List<EmployeeDetailVO> employeeDeptVOList = employees.stream()
                .map(e -> BeanUtil.copyProperties(e, EmployeeDetailVO.class))
                .collect(Collectors.toList());
        return Result.success(employeeDeptVOList, "查询成功");
    }

    @Override
    public Result<List<EmployeeDeptVO>> getEmployeeByDeptIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Result.success(Collections.EMPTY_LIST);
        }
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.in("DEPT_ID", ids);
        List<Employee> employees = employeeMapper.selectList(wrapper);
        List<EmployeeDeptVO> employeeDeptVOList = employees.stream()
                .map(a -> BeanUtil.copyProperties(a, EmployeeDeptVO.class))
                .collect(Collectors.toList());
        return Result.success(employeeDeptVOList);
    }

    /**
     * 处理自定义Tree的字段名称
     * @param treeNodeList
     * @return
     */
    public List<Tree<String>> handleCustomTreeDisplay(List<TreeNode<String>> treeNodeList) {
        // 自定义TreeUtils 字段名
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey("departId");
        treeNodeConfig.setNameKey("departName");

        //转换器
        List<Tree<String>> treeNodes = TreeUtil.build(treeNodeList, "0", treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    //tree.setWeight(treeNode.getWeight());
                    tree.setName(treeNode.getName());
                    //扩展属性 ...
                    tree.putExtra("departLevel", treeNode.getExtra().get("departLevel"));
                });
        return treeNodes;
    }

    /**
     * 递归获得 父id下的所有id
     * @param departmentList 所有部门列表
     * @param pid  指定的PID
     * @return
     */
    public List<String> treeDepartIdList(List<Department> departmentList, String pid) {
        for (Department dept : departmentList) {
            //遍历出父id等于参数的id，add进子节点集合
            if (dept.getPid().equals(pid)) {
                //递归遍历下一级
                treeDepartIdList(departmentList, String.valueOf(dept.getId()));
                childDeptId.add(String.valueOf(dept.getId()));
            }
        }
        return childDeptId;
    }


    /**
     * 递归获得 所有上级部门列表
     * @param departmentAllList 所有部门列表
     * @param departmentChildList 子部门列表
     * @return
     */
    public void childParentList(List<Department> departmentAllList,
                                            List<Department> departmentChildList,
                                            List<Department> result) {

        for (Department child : departmentChildList) {
            if (child.getPid().equals("0")) {
                result.add(child);
                continue;
            }

            Department depart = departmentAllList.stream()
                    .filter(department -> child.getPid().equals(String.valueOf(department.getId())))
                    .findFirst()
                    .get();

            result.add(depart);

            if (!depart.getPid().equals("0")) {
                childParentList(departmentAllList, Arrays.asList(depart), result);
            }
        }

    }
}