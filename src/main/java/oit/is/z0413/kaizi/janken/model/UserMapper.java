package oit.is.z0413.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Select("SELECT * from users")
  ArrayList<User> selectAll();

  @Select("SELECT * from users where id = #{id}")
  User selectById(int id);

  @Select("SELECT * from users where name = #{name}")
  User selectByname(String name);

  /**
   * #{user}などはinsertの引数にあるUserクラスのフィールドを表しています 引数に直接String userなどと書いてもいけるはず
   * 下記のOptionsを指定すると，insert実行時にAuto incrementされたIDの情報を取得できるようになる useGeneratedKeys
   * = true -> Keyは自動生成されることを表す keyColumn : keyになるテーブルのカラム名 keyProperty :
   * keyになるJavaクラスのフィールド名
   *
   * @param User
   */
  @Insert("INSERT INTO User (user,number) VALUES (#{user},#{number});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertUser(User User);

}
