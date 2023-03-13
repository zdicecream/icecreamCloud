CREATE TABLE xxl_job_info (
                              id int NOT NULL AUTO_INCREMENT,
                              job_group int NOT NULL COMMENT '执行器主键ID',
                              job_desc varchar(255) NOT NULL,
                              add_time TIMESTAMP DEFAULT NULL,
                              update_time TIMESTAMP DEFAULT NULL,
                              author varchar(64) DEFAULT NULL COMMENT '作者',
                              alarm_email varchar(255) DEFAULT NULL COMMENT '报警邮件',
                              schedule_type varchar(50) NOT NULL DEFAULT 'NONE' COMMENT '调度类型',
                              schedule_conf varchar(128) DEFAULT NULL COMMENT '调度配置，值含义取决于调度类型',
                              misfire_strategy varchar(50) NOT NULL DEFAULT 'DO_NOTHING' COMMENT '调度过期策略',
                              executor_route_strategy varchar(50) DEFAULT NULL COMMENT '执行器路由策略',
                              executor_handler varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
                              executor_param varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
                              executor_block_strategy varchar(50) DEFAULT NULL COMMENT '阻塞处理策略',
                              executor_timeout int NOT NULL DEFAULT '0' COMMENT '任务执行超时时间，单位秒',
                              executor_fail_retry_count int NOT NULL DEFAULT '0' COMMENT '失败重试次数',
                              glue_type varchar(50) NOT NULL COMMENT 'GLUE类型',
                              glue_source Blob COMMENT 'GLUE源代码',
                              glue_remark varchar(128) DEFAULT NULL COMMENT 'GLUE备注',
                              glue_updatetime TIMESTAMP DEFAULT NULL COMMENT 'GLUE更新时间',
                              child_jobid varchar(255) DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
                              trigger_status tinyint NOT NULL DEFAULT '0' COMMENT '调度状态：0-停止，1-运行',
                              trigger_last_time bigint NOT NULL DEFAULT '0' COMMENT '上次调度时间',
                              trigger_next_time bigint NOT NULL DEFAULT '0' COMMENT '下次调度时间',
                              PRIMARY KEY (id)
);

CREATE TABLE xxl_job_log (
                             id bigint NOT NULL AUTO_INCREMENT,
                             job_group int NOT NULL COMMENT '执行器主键ID',
                             job_id int NOT NULL COMMENT '任务，主键ID',
                             executor_address varchar(255) DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
                             executor_handler varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
                             executor_param varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
                             executor_sharding_param varchar(20) DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
                             executor_fail_retry_count int NOT NULL DEFAULT '0' COMMENT '失败重试次数',
                             trigger_time TIMESTAMP DEFAULT NULL COMMENT '调度-时间',
                             trigger_code int NOT NULL COMMENT '调度-结果',
                             trigger_msg Blob COMMENT '调度-日志',
                             handle_time TIMESTAMP DEFAULT NULL COMMENT '执行-时间',
                             handle_code int NOT NULL COMMENT '执行-状态',
                             handle_msg Blob COMMENT '执行-日志',
                             alarm_status tinyint NOT NULL DEFAULT '0' COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
                             PRIMARY KEY (id)
);

CREATE TABLE xxl_job_log_report (
                                    id int NOT NULL AUTO_INCREMENT,
                                    trigger_day TIMESTAMP DEFAULT NULL COMMENT '调度-时间',
                                    running_count int NOT NULL DEFAULT '0' COMMENT '运行中-日志数量',
                                    suc_count int NOT NULL DEFAULT '0' COMMENT '执行成功-日志数量',
                                    fail_count int NOT NULL DEFAULT '0' COMMENT '执行失败-日志数量',
                                    update_time TIMESTAMP DEFAULT NULL,
                                    PRIMARY KEY (id)
);

CREATE TABLE xxl_job_logglue (
                                 id int NOT NULL AUTO_INCREMENT,
                                 job_id int NOT NULL COMMENT '任务，主键ID',
                                 glue_type varchar(50) DEFAULT NULL COMMENT 'GLUE类型',
                                 glue_source Blob COMMENT 'GLUE源代码',
                                 glue_remark varchar(128) NOT NULL COMMENT 'GLUE备注',
                                 add_time TIMESTAMP DEFAULT NULL,
                                 update_time TIMESTAMP DEFAULT NULL,
                                 PRIMARY KEY (id)
);

CREATE TABLE xxl_job_registry (
                                  id int NOT NULL AUTO_INCREMENT,
                                  registry_group varchar(50) NOT NULL,
                                  registry_key varchar(255) NOT NULL,
                                  registry_value varchar(255) NOT NULL,
                                  update_time TIMESTAMP DEFAULT NULL,
                                  PRIMARY KEY (id)
);

CREATE TABLE xxl_job_group (
                               id int NOT NULL AUTO_INCREMENT,
                               app_name varchar(64) NOT NULL COMMENT '执行器AppName',
                               title varchar(12) NOT NULL COMMENT '执行器名称',
                               address_type tinyint NOT NULL DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
                               address_list Blob COMMENT '执行器地址列表，多地址逗号分隔',
                               update_time TIMESTAMP DEFAULT NULL,
                               PRIMARY KEY (id)
);

CREATE TABLE xxl_job_user (
                              id int NOT NULL AUTO_INCREMENT,
                              username varchar(50) NOT NULL COMMENT '账号',
                              password varchar(50) NOT NULL COMMENT '密码',
                              role tinyint NOT NULL COMMENT '角色：0-普通用户、1-管理员',
                              permission varchar(255) DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
                              PRIMARY KEY (id)
);

CREATE TABLE xxl_job_lock (
                              lock_name varchar(50) NOT NULL COMMENT '锁名称',
                              PRIMARY KEY (lock_name)
);

create index I_trigger_time on xxl_job_log(trigger_time);
create index I_handle_code on  xxl_job_log(handle_code);
create index i_trigger_day on xxl_job_log_report(trigger_day);
create index i_g_k_v on xxl_job_registry(registry_group,registry_key,registry_value);
create index i_username on xxl_job_user(username);
