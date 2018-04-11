package results.sso;

import annotations.DataField;

public class PersonResult extends Result {
    
    public PersonData data;
    
    public class PersonData {
        @DataField(name = "用户id")
        public Long personId;
        @DataField(name = "用户名")
        public String username;
        @DataField(name = "手机号")
        public String phone;
        @DataField(name = "邮箱")
        public String email;
        @DataField(name = "编号")
        public String number;
        @DataField(name = "姓名")
        public String name;
        @DataField(name = "头像")
        public String avatar;
        @DataField(name = "备注")
        public String remark;
        @DataField(name = "简介")
        public String intro;
        @DataField(name = "座机")
        public String landline;
        @DataField(name = "办公地点")
        public String office;
        @DataField(name = "出生日期")
        public Long birthday;
        @DataField(name = "毕业日期")
        public Long graduateday;
        @DataField(name = "面试时间")
        public Long interviewday;
        @DataField(name = "入职时间")
        public Long entryday;
        @DataField(name = "学校")
        public String school;
        @DataField(name = "专业")
        public String major;
        @DataField(name = "籍贯")
        public String register;
        @DataField(name = "性别")
        public Integer sex;
        @DataField(name = "类型")
        public Integer type;
        @DataField(name = "组织id")
        public Long organizeId;
        
        @DataField(name = "accesstoken")
        public String accesstoken;
        
        @DataField(name = "更新时间")
        public Long updateTime;
        @DataField(name = "是否删除")
        public Integer deleted;
        
        public PersonData() {
        }
    }
    
    public PersonResult() {
    }
    
}
