package results.sso;

import annotations.DataField;
import models.sso.SsoOrganize;
import models.sso.SsoPerson;

public class PersonResult extends Result {
    
    public PersonData data;
    
    public static class PersonData {
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
        @DataField(name = "机构Id")
        public Long organizeId;
        
        @DataField(name = "accesstoken")
        public String accesstoken = "";
        
        @DataField(name = "更新时间")
        public Long updateTime;
        @DataField(name = "是否删除")
        public Integer deleted;
        
        public PersonData() {
        
        }
        
        public PersonData(SsoPerson person) {
            this.personId = person.ssoId;
            this.username = person.username;
            //this.password = person.password;
            this.zone = person.zone;
            this.phone = person.phone;
            this.email = person.email;
            this.name = person.name;
            this.number = person.number;
            this.nickname = person.nickname;
            this.pinyin = person.pinyin;
            this.avatar = person.avatar;
            this.birthday = person.birthday;
            this.intro = person.intro;
            if (person.sex != null) {
                this.sex = person.sex.code();
            }
            if (person.type != null) {
                this.type = person.type.code();
            }
            if (person.organize != null) {
                this.organizeId = ((SsoOrganize) person.organize).ssoId;
            }
        }
        
    }
    
    public PersonResult() {
    }
    
}
