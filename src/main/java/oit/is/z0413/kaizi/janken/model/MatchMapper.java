package oit.is.z0413.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {

  @Select("SELECT * from matches where id = #{id}")
  Match selectById(int id);

  @Select("SELECT * FROM matches")
  ArrayList<Match> selectAll();

  /**
   * #{user}などはinsertの引数にあるUserクラスのフィールドを表しています 引数に直接String userなどと書いてもいけるはず
   * 下記のOptionsを指定すると，insert実行時にAuto incrementされたIDの情報を取得できるようになる useGeneratedKeys
   * = true -> Keyは自動生成されることを表す keyColumn : keyになるテーブルのカラム名 keyProperty :
   * keyになるJavaクラスのフィールド名
   *
   * @param Match
   */
  @Update("UPDATE Match SET user1=#{user1}, user2=#{user2} WHERE ID = #{id}")
  void updateById(Match match);

  @Insert("INSERT INTO matches (user1,user2,user1Hand,user2Hand) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatch(Match match);

}
