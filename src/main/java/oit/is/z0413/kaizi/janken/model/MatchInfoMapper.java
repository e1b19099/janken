package oit.is.z0413.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {

  @Select("SELECT * FROM matchinfo")
  ArrayList<MatchInfo> selectAll();

  @Select("SELECT * from matchinfo where isActive = #{isActive}")
  ArrayList<MatchInfo> selectByisActive(boolean isActive);

  @Select("SELECT * from matchinfo where id = #{id}")
  MatchInfo selectById(int id);

  @Select("SELECT * from matchinfo where user1 = #{user2} and user2 = #{user1} and isActive = true")
  MatchInfo selectByuser(int user1, int user2);

  /**
   * #{user}などはinsertの引数にあるUserクラスのフィールドを表しています 引数に直接String userなどと書いてもいけるはず
   * 下記のOptionsを指定すると，insert実行時にAuto incrementされたIDの情報を取得できるようになる useGeneratedKeys
   * = true -> Keyは自動生成されることを表す keyColumn : keyになるテーブルのカラム名 keyProperty :
   * keyになるJavaクラスのフィールド名
   *
   * @param Match
   */
  @Update("UPDATE matchinfo SET isActive = false WHERE ID = #{id}")
  void updateFById(int id);

  @Insert("INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchInfo(MatchInfo matchInfo);

}
