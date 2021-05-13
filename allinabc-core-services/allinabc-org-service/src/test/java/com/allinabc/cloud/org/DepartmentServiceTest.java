package com.allinabc.cloud.org;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.alibaba.fastjson.JSONObject;
import com.allinabc.cloud.org.pojo.po.Department;
import com.allinabc.cloud.org.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * @author QQF
 * @date 2020/12/24 15:31
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OrgApplication.class)
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void save(){
        Department dept = new Department();
        dept.setAdGroupName("groupName");
        dept.setAssistant("Assistant");
        dept.setCode("code");
        dept.setDisableDate(new Date());
        departmentService.save(dept);
    }

    @Test
    public void find(){

        Department dept = departmentService.selectById("1342012578138890241");
        System.out.println(dept.toString());

    }

    @Test
    public void update(){
        Department dept = departmentService.selectById("1342012578138890241");
        dept.setNameEn("nameEn");
        dept = departmentService.update(dept.getId().toString(),dept);
        System.out.println(dept.toString());
    }

    @Test
    public void del(){
        departmentService.deleteById("1342012578138890241");
    }

    /**
     * Hutool中TreeUtil使用
     */
    @Ignore
    public void testTreeUtils() {
        // 构建node列表
        List<TreeNode<String>> nodeList = CollUtil.newArrayList();

        nodeList.add(new TreeNode<>("1", "0", "系统管理", 5));
        nodeList.add(new TreeNode<>("11", "1", "用户管理", 222222));
        nodeList.add(new TreeNode<>("111", "11", "用户添加", 0));
        nodeList.add(new TreeNode<>("2", "0", "店铺管理", 1));
        nodeList.add(new TreeNode<>("21", "2", "商品管理", 44));
        nodeList.add(new TreeNode<>("221", "2", "商品管理2", 2));

        List<Tree<String>> treeList = TreeUtil.build(nodeList, "0");
        log.info("{}", JSONObject.toJSONString(treeList));
    }

    @Test
    public void testFindParent() {

    }


}