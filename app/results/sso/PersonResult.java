package results.sso;

import annotations.DataField;

public class PersonResult extends Result {
    
    public PersonData data;
    
    public class PersonData {
        @DataField(name = "用户id")
        public Long personId;
        @DataField(name = "用户名")
        public String username;
        @DataField(name = "密码")
        public String password;
        @DataField(name = "地区")
        public String zone;
        @DataField(name = "手机号")
        public String phone;
        @DataField(name = "邮箱")
        public String email;
        @DataField(name = "姓名")
        public String name;
        @DataField(name = "编号")
        public String number;
        @DataField(name = "昵称")
        public String nickname;
        @DataField(name = "拼音")
        public String pinyin;
        @DataField(name = "头像")
        public String avatar;
        @DataField(name = "出生日期")
        public Long birthday;
        @DataField(name = "简介")
        public String intro;
        @DataField(name = "性别")
        public Integer sex;
        @DataField(name = "类型")
        public Integer type;
        @DataField(name = "组织rootId")
        public Long rootId;
        
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
