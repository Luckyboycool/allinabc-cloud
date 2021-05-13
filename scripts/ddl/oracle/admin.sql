/***********************************ORACLE************************************************/
-- we don't know how to generate schema ADMINDA1 (class Schema) :(
create table ADM_ATTACHMENT
(
	ID VARCHAR2(32) not null
		constraint PK_SYS_ATTACHMENT
			primary key,
	APP_CODE VARCHAR2(50),
	FORM_TYPE VARCHAR2(50),
	FORM_ID VARCHAR2(32),
	CATEGORY VARCHAR2(50),
	STORE_NAME VARCHAR2(50),
	FILE_NAME VARCHAR2(50),
	FILE_PATH VARCHAR2(500),
	FILE_EXTENSION VARCHAR2(50),
	REMARK VARCHAR2(500),
	CREATED_BY VARCHAR2(32),
	CREATE_TM TIMESTAMP(6) default null,
	UPDATED_BY VARCHAR2(32),
	UPDATE_TM TIMESTAMP(6) default null
)
/

comment on table ADM_ATTACHMENT is '附件表'
/

comment on column ADM_ATTACHMENT.ID is '主键'
/

comment on column ADM_ATTACHMENT.APP_CODE is '程序代码'
/

comment on column ADM_ATTACHMENT.FORM_TYPE is '表单类型'
/

comment on column ADM_ATTACHMENT.FORM_ID is '表单ID'
/

comment on column ADM_ATTACHMENT.CATEGORY is '附件分类'
/

comment on column ADM_ATTACHMENT.STORE_NAME is '存储名称'
/

comment on column ADM_ATTACHMENT.FILE_NAME is '附件名称'
/

comment on column ADM_ATTACHMENT.FILE_PATH is '附件路径'
/

comment on column ADM_ATTACHMENT.FILE_EXTENSION is '附件后缀'
/

comment on column ADM_ATTACHMENT.REMARK is '备注'
/

comment on column ADM_ATTACHMENT.CREATED_BY is '创建人'
/

comment on column ADM_ATTACHMENT.CREATE_TM is '创建时间'
/

comment on column ADM_ATTACHMENT.UPDATED_BY is '更新人'
/

comment on column ADM_ATTACHMENT.UPDATE_TM is '更新时间'
/

create index IDX_ATTACHMENT_CODE
	on ADM_ATTACHMENT (APP_CODE)
/

create table ADM_BASIC_FORM
(
	ID VARCHAR2(32) not null
		constraint PK_SYS_BASIC_FORM
			primary key,
	PID VARCHAR2(32),
	VERSION NUMBER(8) default null,
	SUBJECT VARCHAR2(100),
	REQUEST_NO VARCHAR2(50),
	APP_CODE VARCHAR2(50),
	FORM_TYPE VARCHAR2(50),
	PROCESS_ID VARCHAR2(32),
	INSTANCE_ID VARCHAR2(32),
	DRAFTER VARCHAR2(32),
	REQUESTER VARCHAR2(32),
	REQUEST_TIME TIMESTAMP(6) default null,
	IS_DRAFT VARCHAR2(1),
	STATUS VARCHAR2(50),
	REMARK VARCHAR2(100),
	CREATED_BY VARCHAR2(32),
	CREATE_TM TIMESTAMP(6) default null,
	UPDATED_BY VARCHAR2(32),
	UPDATE_TM TIMESTAMP(6) default null
)
/

comment on column ADM_BASIC_FORM.ID is '业务表单ID'
/

comment on column ADM_BASIC_FORM.PID is '父表单ID'
/

comment on column ADM_BASIC_FORM.VERSION is '版本'
/

comment on column ADM_BASIC_FORM.SUBJECT is '主题'
/

comment on column ADM_BASIC_FORM.REQUEST_NO is '申请单号'
/

comment on column ADM_BASIC_FORM.APP_CODE is '程序代码'
/

comment on column ADM_BASIC_FORM.FORM_TYPE is '表单类型'
/

comment on column ADM_BASIC_FORM.PROCESS_ID is '流程ID'
/

comment on column ADM_BASIC_FORM.INSTANCE_ID is '实例ID'
/

comment on column ADM_BASIC_FORM.DRAFTER is '撰稿人'
/

comment on column ADM_BASIC_FORM.REQUESTER is '申请人'
/

comment on column ADM_BASIC_FORM.REQUEST_TIME is '申请时间'
/

comment on column ADM_BASIC_FORM.IS_DRAFT is '是否为草稿'
/

comment on column ADM_BASIC_FORM.STATUS is '表单状态'
/

comment on column ADM_BASIC_FORM.REMARK is '备注'
/

comment on column ADM_BASIC_FORM.CREATED_BY is '创建人'
/

comment on column ADM_BASIC_FORM.CREATE_TM is '创建时间'
/

comment on column ADM_BASIC_FORM.UPDATED_BY is '更新人'
/

comment on column ADM_BASIC_FORM.UPDATE_TM is '更新时间'
/

create table ADM_CONFIG
(
	ID VARCHAR2(32) not null
		constraint PK_SYS_CONFIG
			primary key,
	APP_CODE VARCHAR2(50),
	CONFIG_TYPE VARCHAR2(50),
	CONFIG_KEY VARCHAR2(50),
	CONFIG_NAME VARCHAR2(50),
	CONFIG_VALUE VARCHAR2(200),
	REMARK VARCHAR2(500),
	CREATED_BY VARCHAR2(32),
	CREATE_TM TIMESTAMP(6) default null,
	UPDATED_BY VARCHAR2(32),
	UPDATE_TM TIMESTAMP(6) default null
)
/

comment on column ADM_CONFIG.ID is '系统配置ID'
/

comment on column ADM_CONFIG.APP_CODE is '程序代码'
/

comment on column ADM_CONFIG.CONFIG_TYPE is '配置类型：系统内置'
/

comment on column ADM_CONFIG.CONFIG_KEY is '配置代码'
/

comment on column ADM_CONFIG.CONFIG_NAME is '配置显示名称'
/

comment on column ADM_CONFIG.CONFIG_VALUE is '配置值'
/

comment on column ADM_CONFIG.REMARK is '备注'
/

comment on column ADM_CONFIG.CREATED_BY is '创建人'
/

comment on column ADM_CONFIG.CREATE_TM is '创建时间'
/

comment on column ADM_CONFIG.UPDATED_BY is '更新人'
/

comment on column ADM_CONFIG.UPDATE_TM is '更新时间'
/

create index IDX_CONFIG_TYPE
	on ADM_CONFIG (APP_CODE, CONFIG_KEY)
/

create table ADM_DICT_DATA
(
	ID VARCHAR2(32) not null
		constraint PK_SYS_DICT_DATA
			primary key,
	DICT_TYPE VARCHAR2(50),
	DICT_LABEL VARCHAR2(50),
	DICT_VALUE VARCHAR2(50),
	DICT_SORT NUMBER(8) default null,
	IS_DEFAULT VARCHAR2(1),
	LIST_CLASS VARCHAR2(50),
	CSS_CLASS VARCHAR2(50),
	STATUS VARCHAR2(10),
	REMARK VARCHAR2(500),
	CREATED_BY VARCHAR2(32),
	CREATE_TM TIMESTAMP(6) default null,
	UPDATED_BY VARCHAR2(32),
	UPDATE_TM TIMESTAMP(6) default null
)
/

comment on column ADM_DICT_DATA.ID is '系统配置数据ID'
/

comment on column ADM_DICT_DATA.DICT_TYPE is '字典类型'
/

comment on column ADM_DICT_DATA.DICT_LABEL is '字典数据标签'
/

comment on column ADM_DICT_DATA.DICT_VALUE is '字典数据值'
/

comment on column ADM_DICT_DATA.DICT_SORT is '字典数据顺序'
/

comment on column ADM_DICT_DATA.IS_DEFAULT is '是否默认'
/

comment on column ADM_DICT_DATA.LIST_CLASS is '表格样式'
/

comment on column ADM_DICT_DATA.CSS_CLASS is '列表CSS样式'
/

comment on column ADM_DICT_DATA.STATUS is '状态'
/

comment on column ADM_DICT_DATA.REMARK is '备注'
/

comment on column ADM_DICT_DATA.CREATED_BY is '创建人'
/

comment on column ADM_DICT_DATA.CREATE_TM is '创建时间'
/

comment on column ADM_DICT_DATA.UPDATED_BY is '更新人'
/

comment on column ADM_DICT_DATA.UPDATE_TM is '更新时间'
/

create index IDX_DICT_TYPE
	on ADM_DICT_DATA (DICT_TYPE)
/

create table ADM_DICT_TYPE
(
	ID VARCHAR2(32) not null
		constraint PK_SYS_DICT_TYPE
			primary key,
	APP_CODE VARCHAR2(10),
	DICT_TYPE VARCHAR2(50),
	DICT_NAME VARCHAR2(50),
	STATUS VARCHAR2(10),
	REMARK VARCHAR2(500),
	CREATED_BY VARCHAR2(32),
	CREATE_TM TIMESTAMP(6) default null,
	UPDATED_BY VARCHAR2(32),
	UPDATE_TM TIMESTAMP(6) default null,
	constraint IDX_CODE_TYPE
		unique (APP_CODE, DICT_TYPE)
)
/

comment on table ADM_DICT_TYPE is '字典类型'
/

comment on column ADM_DICT_TYPE.ID is '系统配置ID'
/

comment on column ADM_DICT_TYPE.APP_CODE is '程序代码'
/

comment on column ADM_DICT_TYPE.DICT_TYPE is '字典类型'
/

comment on column ADM_DICT_TYPE.DICT_NAME is '字典名称'
/

comment on column ADM_DICT_TYPE.STATUS is '字典状态'
/

comment on column ADM_DICT_TYPE.REMARK is '备注'
/

comment on column ADM_DICT_TYPE.CREATED_BY is '创建人'
/

comment on column ADM_DICT_TYPE.CREATE_TM is '创建时间'
/

comment on column ADM_DICT_TYPE.UPDATED_BY is '更新人'
/

comment on column ADM_DICT_TYPE.UPDATE_TM is '更新时间'
/

create table ADM_GROUP
(
	ID VARCHAR2(32) not null
		constraint PK_SYS_GROUP
			primary key,
	APP_CODE VARCHAR2(50),
	GROUP_NAME VARCHAR2(100),
	TYPE VARCHAR2(10),
	IS_AVAILABLE VARCHAR2(1),
	IS_HIDDEN VARCHAR2(1),
	REMARK VARCHAR2(500),
	CREATED_BY VARCHAR2(32),
	CREATE_TM TIMESTAMP(6) default null,
	UPDATED_BY VARCHAR2(32),
	UPDATE_TM TIMESTAMP(6) default null
)
/

comment on column ADM_GROUP.ID is '群组ID'
/

comment on column ADM_GROUP.APP_CODE is '程序代码'
/

comment on column ADM_GROUP.GROUP_NAME is '群组名称'
/

comment on column ADM_GROUP.TYPE is '群组类型(1:主管群组)'
/

comment on column ADM_GROUP.IS_AVAILABLE is '是否可用'
/

comment on column ADM_GROUP.IS_HIDDEN is '是否隐藏'
/

comment on column ADM_GROUP.REMARK is '备注'
/

comment on column ADM_GROUP.CREATED_BY is '创建人'
/

comment on column ADM_GROUP.CREATE_TM is '创建时间'
/

comment on column ADM_GROUP.UPDATED_BY is '更新人'
/

comment on column ADM_GROUP.UPDATE_TM is '更新时间'
/

create index IDX_GROUP_CODE
	on ADM_GROUP (APP_CODE)
/

create unique index IDX_GROUP_CODE_NAME
	on ADM_GROUP (APP_CODE, GROUP_NAME)
/

create table ADM_GROUP_USER
(
	GROUP_ID VARCHAR2(32),
	USER_ID VARCHAR2(32)
)
/

comment on column ADM_GROUP_USER.GROUP_ID is '群组ID'
/

comment on column ADM_GROUP_USER.USER_ID is '用户ID'
/

create table ADM_JOB
(
	ID VARCHAR2(32) not null
		constraint PK_SYS_JOB
			primary key,
	APP_CODE VARCHAR2(100),
	JOB_NAME VARCHAR2(100),
	JOB_GROUP VARCHAR2(100),
	INVOKE_TARGET VARCHAR2(500),
	CRON_EXPRESS VARCHAR2(500),
	MISFIRE_POLICY VARCHAR2(100),
	CONCURRENT VARCHAR2(1),
	STATUS VARCHAR2(1),
	REMARK VARCHAR2(255),
	CREATED_BY VARCHAR2(32),
	CREATE_TM TIMESTAMP(6) default null,
	UPDATED_BY VARCHAR2(32),
	UPDATE_TM TIMESTAMP(6) default null
)
/

comment on column ADM_JOB.ID is '任务主键'
/

comment on column ADM_JOB.APP_CODE is '应用代码'
/

comment on column ADM_JOB.JOB_NAME is '任务名称'
/

comment on column ADM_JOB.JOB_GROUP is '任务组名'
/

comment on column ADM_JOB.INVOKE_TARGET is '调用目标字符串'
/

comment on column ADM_JOB.CRON_EXPRESS is 'CRON表达式'
/

comment on column ADM_JOB.MISFIRE_POLICY is '计划执行错误策略（1立即执行 2执行一次 3放弃执行）'
/

comment on column ADM_JOB.CONCURRENT is '是否并发执行（0允许 1禁止）'
/

comment on column ADM_JOB.STATUS is '状态（0正常 1暂停）'
/

comment on column ADM_JOB.REMARK is '备注'
/

comment on column ADM_JOB.CREATED_BY is '创建者'
/

comment on column ADM_JOB.CREATE_TM is '创建时间'
/

comment on column ADM_JOB.UPDATED_BY is '更新者'
/

comment on column ADM_JOB.UPDATE_TM is '更新时间'
/

create table ADM_JOB_LOG
(
	ID VARCHAR2(32) not null
		constraint PK_SYS_JOB_LOG
			primary key,
	APP_CODE VARCHAR2(100),
	JOB_GROUP VARCHAR2(64),
	JOB_NAME VARCHAR2(64),
	INVOKE_TARGET VARCHAR2(500),
	JOB_MSG VARCHAR2(500),
	STATUS VARCHAR2(1),
	EXCEPTION_INFO VARCHAR2(2000),
	CREATE_TM TIMESTAMP(6) default null
)
/

comment on column ADM_JOB_LOG.ID is '任务日志ID'
/

comment on column ADM_JOB_LOG.APP_CODE is '程序代码'
/

comment on column ADM_JOB_LOG.JOB_GROUP is '任务组名'
/

comment on column ADM_JOB_LOG.JOB_NAME is '任务名称'
/

comment on column ADM_JOB_LOG.INVOKE_TARGET is '调用目标字符串'
/

comment on column ADM_JOB_LOG.JOB_MSG is '日志信息'
/

comment on column ADM_JOB_LOG.STATUS is '执行状态（0正常 1失败）'
/

comment on column ADM_JOB_LOG.EXCEPTION_INFO is '异常信息'
/

comment on column ADM_JOB_LOG.CREATE_TM is '创建时间'
/

create table ADM_LOGIN_INFO
(
	ID VARCHAR2(32) not null
		constraint PK_SYS_LOGIN_INFO
			primary key,
	USER_NAME VARCHAR2(100),
	LOGIN_LOCATION VARCHAR2(100),
	BROWSER VARCHAR2(100),
	IP_ADDR VARCHAR2(100),
	LOGIN_TIME TIMESTAMP(6) default null,
	MESSAGE VARCHAR2(500),
	OS_TYPE VARCHAR2(50),
	STATUS VARCHAR2(10)
)
/

comment on column ADM_LOGIN_INFO.ID is '登录日志ID'
/

comment on column ADM_LOGIN_INFO.USER_NAME is '用户名'
/

comment on column ADM_LOGIN_INFO.LOGIN_LOCATION is '登录地址'
/

comment on column ADM_LOGIN_INFO.BROWSER is '浏览器'
/

comment on column ADM_LOGIN_INFO.IP_ADDR is 'IP地址'
/

comment on column ADM_LOGIN_INFO.LOGIN_TIME is '登录时间'
/

comment on column ADM_LOGIN_INFO.MESSAGE is '提示消息'
/

comment on column ADM_LOGIN_INFO.OS_TYPE is '操作系统'
/

comment on column ADM_LOGIN_INFO.STATUS is '登录状态'
/

create table ADM_NOTICE
(
	ID VARCHAR2(32) not null
		constraint PK_SYS_NOTICE
			primary key,
	NOTICE_TYPE VARCHAR2(50),
	NOTICE_TITLE VARCHAR2(50),
	NOTICE_CONTENT VARCHAR2(500),
	STATUS VARCHAR2(10),
	REMARK VARCHAR2(500),
	CREATED_BY VARCHAR2(32),
	CREATE_TM TIMESTAMP(6) default null,
	UPDATED_BY VARCHAR2(32),
	UPDATE_TM TIMESTAMP(6) default null
)
/

comment on column ADM_NOTICE.ID is '通知ID'
/

comment on column ADM_NOTICE.NOTICE_TYPE is '通知类型'
/

comment on column ADM_NOTICE.NOTICE_TITLE is '通知标题'
/

comment on column ADM_NOTICE.NOTICE_CONTENT is '正文'
/

comment on column ADM_NOTICE.STATUS is '状态'
/

comment on column ADM_NOTICE.REMARK is '备注'
/

comment on column ADM_NOTICE.CREATED_BY is '创建人'
/

comment on column ADM_NOTICE.CREATE_TM is '创建时间'
/

comment on column ADM_NOTICE.UPDATED_BY is '更新人'
/

comment on column ADM_NOTICE.UPDATE_TM is '更新时间'
/

create table ADM_OPER_LOG
(
	ID VARCHAR2(32) not null
		constraint PK_SYS_OPER_LOG
			primary key,
	TITLE VARCHAR2(255) default null,
	BUSINESS_TYPE NUMBER(8) default null,
	METHOD VARCHAR2(500) default null,
	REQUEST_METHOD VARCHAR2(100) default null,
	OPERATOR_TYPE NUMBER(8) default null,
	OPER_NAME VARCHAR2(100) default null,
	OPER_URL VARCHAR2(255) default null,
	OPER_IP VARCHAR2(100) default null,
	OPER_LOCATION VARCHAR2(100) default null,
	OPER_PARAM VARCHAR2(2000) default null,
	JSON_RESULT VARCHAR2(500) default null,
	STATUS NUMBER(10) default null,
	ERROR_MSG VARCHAR2(500) default null,
	OPER_TIME TIMESTAMP(6) default null
)
/

comment on column ADM_OPER_LOG.ID is '日志ID'
/

comment on column ADM_OPER_LOG.TITLE is '操作模块'
/

comment on column ADM_OPER_LOG.BUSINESS_TYPE is '业务类型（0其它 1新增 2修改 3删除）'
/

comment on column ADM_OPER_LOG.METHOD is '请求方法'
/

comment on column ADM_OPER_LOG.REQUEST_METHOD is '请求方式'
/

comment on column ADM_OPER_LOG.OPERATOR_TYPE is '操作类别（0其它 1后台用户 2手机端用户）'
/

comment on column ADM_OPER_LOG.OPER_NAME is '操作人员'
/

comment on column ADM_OPER_LOG.OPER_URL is '请求url'
/

comment on column ADM_OPER_LOG.OPER_IP is '操作地址'
/

comment on column ADM_OPER_LOG.OPER_LOCATION is '操作地点'
/

comment on column ADM_OPER_LOG.OPER_PARAM is '请求参数'
/

comment on column ADM_OPER_LOG.JSON_RESULT is '返回参数'
/

comment on column ADM_OPER_LOG.STATUS is '操作状态（0=异常,1=正常）'
/

comment on column ADM_OPER_LOG.ERROR_MSG is '错误消息'
/

comment on column ADM_OPER_LOG.OPER_TIME is '操作时间'
/

create table ADM_RESOURCE
(
	ID VARCHAR2(32) not null
		constraint PK_SYS_RESOURCE
			primary key,
	APP_CODE VARCHAR2(20),
	PARENT_ID VARCHAR2(32),
	RES_KEY VARCHAR2(50),
	RES_NAME VARCHAR2(50),
	RES_TYPE VARCHAR2(1),
	COMPONENT VARCHAR2(50),
	PATH VARCHAR2(255),
	PERMS VARCHAR2(100),
	ICON VARCHAR2(100),
	SORT_NO VARCHAR2(6),
	VISIBLE VARCHAR2(1),
	TARGET VARCHAR2(20),
	REDIRECT VARCHAR2(255),
	REMARK VARCHAR2(500),
	CREATED_BY VARCHAR2(32),
	CREATE_TM TIMESTAMP(6) default null,
	UPDATED_BY VARCHAR2(32),
	UPDATE_TM TIMESTAMP(6) default null
)
/

comment on column ADM_RESOURCE.ID is '资源主键'
/

comment on column ADM_RESOURCE.APP_CODE is '程序代码'
/

comment on column ADM_RESOURCE.PARENT_ID is '父资源主键'
/

comment on column ADM_RESOURCE.RES_KEY is '资源编码'
/

comment on column ADM_RESOURCE.RES_NAME is '资源名称'
/

comment on column ADM_RESOURCE.RES_TYPE is '资源类型（M目录 C菜单 F功能）'
/

comment on column ADM_RESOURCE.COMPONENT is '菜单布局'
/

comment on column ADM_RESOURCE.PATH is '链接'
/

comment on column ADM_RESOURCE.PERMS is '权限标识'
/

comment on column ADM_RESOURCE.ICON is '菜单图标'
/

comment on column ADM_RESOURCE.SORT_NO is '显示顺序'
/

comment on column ADM_RESOURCE.VISIBLE is '资源状态（0显示 1隐藏）'
/

comment on column ADM_RESOURCE.TARGET is '打开方式'
/

comment on column ADM_RESOURCE.REDIRECT is '重定向'
/

comment on column ADM_RESOURCE.REMARK is '备注'
/

comment on column ADM_RESOURCE.CREATED_BY is '创建人'
/

comment on column ADM_RESOURCE.CREATE_TM is '创建时间'
/

comment on column ADM_RESOURCE.UPDATED_BY is '更新人'
/

comment on column ADM_RESOURCE.UPDATE_TM is '更新时间'
/

create table ADM_ROLE
(
	ID VARCHAR2(32) not null
		constraint PK_SYS_ROLE
			primary key,
	APP_CODE VARCHAR2(50),
	ROLE_KEY VARCHAR2(50),
	ROLE_NAME VARCHAR2(50),
	SORT_NO NUMBER(8) default null,
	STATUS VARCHAR2(10),
	IS_AVAILABLE VARCHAR2(1),
	REMARK VARCHAR2(500),
	CREATED_BY VARCHAR2(32),
	CREATE_TM TIMESTAMP(6) default null,
	UPDATED_BY VARCHAR2(32),
	UPDATE_TM TIMESTAMP(6) default null
)
/

comment on column ADM_ROLE.ID is '角色ID'
/

comment on column ADM_ROLE.APP_CODE is '应用代码'
/

comment on column ADM_ROLE.ROLE_KEY is '角色标识'
/

comment on column ADM_ROLE.ROLE_NAME is '角色名称'
/

comment on column ADM_ROLE.SORT_NO is '序号'
/

comment on column ADM_ROLE.STATUS is '角色状态'
/

comment on column ADM_ROLE.IS_AVAILABLE is '是否可用'
/

comment on column ADM_ROLE.REMARK is '备注'
/

comment on column ADM_ROLE.CREATED_BY is '创建人'
/

comment on column ADM_ROLE.CREATE_TM is '创建时间'
/

comment on column ADM_ROLE.UPDATED_BY is '更新人'
/

comment on column ADM_ROLE.UPDATE_TM is '更新时间'
/

create index IDX_ROLE_CODE
	on ADM_ROLE (APP_CODE)
/

create table ADM_ROLE_GROUP
(
	ROLE_ID VARCHAR2(32),
	GROUP_ID VARCHAR2(32)
)
/

comment on column ADM_ROLE_GROUP.ROLE_ID is '角色ID'
/

comment on column ADM_ROLE_GROUP.GROUP_ID is '群组ID'
/

create table ADM_ROLE_RESOURCE
(
	ROLE_ID VARCHAR2(32),
	RESOURCE_ID VARCHAR2(32)
)
/

comment on column ADM_ROLE_RESOURCE.ROLE_ID is '角色ID'
/

comment on column ADM_ROLE_RESOURCE.RESOURCE_ID is '功能项ID'
/

create table ADM_ROLE_USER
(
	ROLE_ID VARCHAR2(32),
	USER_ID VARCHAR2(32)
)
/

comment on column ADM_ROLE_USER.ROLE_ID is '角色ID'
/

comment on column ADM_ROLE_USER.USER_ID is '用户ID'
/

create table ADM_USER
(
	ID VARCHAR2(32) not null
		constraint PK_SYS_USER
			primary key,
	USER_NO VARCHAR2(32),
	USER_TYPE VARCHAR2(50),
	NICK_NAME VARCHAR2(100),
	USER_NAME VARCHAR2(100),
	PASSWORD VARCHAR2(100),
	SALT VARCHAR2(100),
	AVATAR VARCHAR2(100),
	EMAIL VARCHAR2(100),
	PHONE VARCHAR2(100),
	SEX VARCHAR2(1),
	STATUS VARCHAR2(10),
	IS_AVAILABLE VARCHAR2(1),
	LAST_LOGIN_IP VARCHAR2(100),
	LAST_LOGIN_TIME TIMESTAMP(6) default null,
	REMARK VARCHAR2(255),
	CREATED_BY VARCHAR2(255),
	CREATE_TM TIMESTAMP(6) default null,
	UPDATED_BY VARCHAR2(255),
	UPDATE_TM TIMESTAMP(6) default null
)
/

comment on column ADM_USER.ID is '用户ID'
/

comment on column ADM_USER.USER_NO is '用户编号'
/

comment on column ADM_USER.USER_TYPE is '用户类型(00 系统用户)'
/

comment on column ADM_USER.NICK_NAME is '用户昵称'
/

comment on column ADM_USER.USER_NAME is '登录名称'
/

comment on column ADM_USER.PASSWORD is '密码'
/

comment on column ADM_USER.SALT is '盐值'
/

comment on column ADM_USER.AVATAR is '头像'
/

comment on column ADM_USER.EMAIL is '用户邮箱'
/

comment on column ADM_USER.PHONE is '手机号码'
/

comment on column ADM_USER.SEX is '性别'
/

comment on column ADM_USER.STATUS is '帐号状态（0正常 1停用 2删除）'
/

comment on column ADM_USER.IS_AVAILABLE is '是否可用（Y/N）'
/

comment on column ADM_USER.LAST_LOGIN_IP is '最后登陆IP'
/

comment on column ADM_USER.LAST_LOGIN_TIME is '最后登陆时间'
/

comment on column ADM_USER.REMARK is '备注'
/

comment on column ADM_USER.CREATED_BY is '创建人'
/

comment on column ADM_USER.CREATE_TM is '创建时间'
/

comment on column ADM_USER.UPDATED_BY is '更新人'
/

comment on column ADM_USER.UPDATE_TM is '更新时间'
/

create table ADM_APPLICATION
(
	ID VARCHAR2(32) not null
		constraint ADM_APPLICATION
			primary key,
	APP_CODE VARCHAR2(255),
	APP_NAME VARCHAR2(255),
	APP_DESC VARCHAR2(255),
	PATH_CODE VARCHAR2(255),
	PATH VARCHAR2(255),
	REMARK VARCHAR2(255),
	CREATED_BY VARCHAR2(255),
	CREATE_TM TIMESTAMP(6),
	UPDATED_BY VARCHAR2(255),
	UPDATE_TM TIMESTAMP(6)
)
/

comment on column ADM_APPLICATION.ID is 'ID'
/

comment on column ADM_APPLICATION.APP_CODE is '系统code'
/

comment on column ADM_APPLICATION.APP_NAME is '系统名称'
/

comment on column ADM_APPLICATION.APP_DESC is '系统描述'
/

comment on column ADM_APPLICATION.PATH_CODE is '路径CODE'
/

comment on column ADM_APPLICATION.PATH is '路径'
/

comment on column ADM_APPLICATION.REMARK is '备注'
/

comment on column ADM_APPLICATION.CREATED_BY is '创建人'
/

comment on column ADM_APPLICATION.CREATE_TM is '创建时间'
/

comment on column ADM_APPLICATION.UPDATED_BY is '更新人'
/

comment on column ADM_APPLICATION.UPDATE_TM is '更新时间'
/

create index IDX_APPLICATION_CODE
	on ADM_APPLICATION (APP_CODE)
/

