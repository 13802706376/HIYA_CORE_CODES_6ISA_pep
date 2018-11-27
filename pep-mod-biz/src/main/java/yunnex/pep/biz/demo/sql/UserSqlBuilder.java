package yunnex.pep.biz.demo.sql;

import org.apache.ibatis.jdbc.SQL;

import yunnex.pep.biz.demo.entity.User;

public class UserSqlBuilder {

    public static final String TABLE_USER = "comment";
    public static final String TABLE_USER_ALIAS = "c";
    public static final String TABLE_USER_WITH_ALIAS = TABLE_USER + " " + TABLE_USER_ALIAS;


    public String queryById() {
        return new SQL() {{
            SELECT("*")
                    .FROM(TABLE_USER_WITH_ALIAS)
                    // .INNER_JOIN()
                    .WHERE("id = #{id}");
        }}.toString();
    }

    public String insertNotNull(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        }
        return new SQL() {{
            /*INSERT_INTO(TABLE_USER);
            if (demo.getName() != null) {
                INTO_COLUMNS("name");
                INTO_VALUES("#{name}");
            }
            if (demo.getCreatedDate() != null) {
                INTO_COLUMNS("created_date");
                INTO_VALUES("#{createdDate}");
            }
            if (demo.getAge() != null) {
                INTO_COLUMNS("age");
                INTO_VALUES("#{age}");
            }*/
        }}.toString();
    }

}
