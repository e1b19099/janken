package oit.is.z0413.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z0413.kaizi.janken.model.Entry;
import oit.is.z0413.kaizi.janken.model.User;
import oit.is.z0413.kaizi.janken.model.UserMapper;
import oit.is.z0413.kaizi.janken.model.Match;
import oit.is.z0413.kaizi.janken.model.MatchMapper;
import oit.is.z0413.kaizi.janken.model.MatchInfo;
import oit.is.z0413.kaizi.janken.model.MatchInfoMapper;

/**
 * /sample3へのリクエストを扱うクラス authenticateの設定をしていれば， /sample3へのアクセスはすべて認証が必要になる
 */
@Controller
@RequestMapping("/")
public class Lec02Controller {

  @Autowired
  private Entry room;

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

  /**
   *
   * @param model Thymeleafにわたすデータを保持するオブジェクト
   * @param prin  ログインユーザ情報が保持されるオブジェクト
   * @return
   *
   *         Transactionalはメソッドでトランザクション処理を実施したい場合に付与する
   *         このメソッドが開始するとトランザクションが開始され，メソッドが正常に終了するとDBへのアクセスが確定する（Runtime
   *         errorなどで止まった場合はロールバックが行われる）
   */

  @GetMapping("/lec02")
  public String lec02(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.room.addUser(loginUser);
    model.addAttribute("login_user", loginUser);
    model.addAttribute("room", this.room);
    ArrayList<User> users = userMapper.selectAll();
    model.addAttribute("users", users);
    ArrayList<Match> match = matchMapper.selectAll();
    model.addAttribute("match", match);
    return "lec02.html";
  }

  @PostMapping("result")
  public String result(@RequestParam Integer id, @RequestParam Integer user2, Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.room.addUser(loginUser);
    model.addAttribute("login_user", loginUser);
    User user = userMapper.selectByname(loginUser);
    int user1 = user.getId();
    String user1Hand = "";
    if (id == 1) {
      model.addAttribute("ch", loginUser);
      user1Hand = "ch";
    }
    if (id == 2) {
      model.addAttribute("gu", loginUser);
      user1Hand = "gu";
    }
    if (id == 3) {
      model.addAttribute("pa", loginUser);
      user1Hand = "pa";
    }
    User users = userMapper.selectById(user2);
    model.addAttribute("users", users);
    Match match2 = new Match();
    match2.setId(1);
    match2.setUser1(user1);
    match2.setUser2(user2);
    match2.setUser1Hand(user1Hand);
    match2.setUser2Hand("gu");
    matchMapper.insertMatch(match2);
    model.addAttribute("match", match2);
    return "match.html";

  }

  @GetMapping("match")
  public String match(@RequestParam Integer id, Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.room.addUser(loginUser);
    model.addAttribute("login_user", loginUser);
    User users = userMapper.selectById(id);
    model.addAttribute("users", users);
    return "match.html";
  }

  @GetMapping("wait/1")
  @Transactional
  public String wait1(@RequestParam Integer user2, Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.room.addUser(loginUser);
    model.addAttribute("login_user", loginUser);
    User user = userMapper.selectByname(loginUser);
    int user1 = user.getId();
    model.addAttribute("ch", loginUser);
    String user1Hand = "Choki";
    User users = userMapper.selectById(user2);
    model.addAttribute("users", users);
    MatchInfo matchinfo = new MatchInfo();
    matchinfo.setUser1(user1);
    matchinfo.setUser2(user2);
    matchinfo.setUser1Hand(user1Hand);
    matchinfo.setisActive(true);
    matchInfoMapper.insertMatchInfo(matchinfo);
    model.addAttribute("matchinfo", matchinfo);
    return "wait.html";
  }

  @GetMapping("wait/2")
  @Transactional
  public String wait2(@RequestParam Integer user2, Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.room.addUser(loginUser);
    model.addAttribute("login_user", loginUser);
    User user = userMapper.selectByname(loginUser);
    int user1 = user.getId();
    model.addAttribute("gu", loginUser);
    String user1Hand = "Gu";
    User users = userMapper.selectById(user2);
    model.addAttribute("users", users);
    MatchInfo matchinfo = new MatchInfo();
    matchinfo.setUser1(user1);
    matchinfo.setUser2(user2);
    matchinfo.setUser1Hand(user1Hand);
    matchinfo.setisActive(true);
    matchInfoMapper.insertMatchInfo(matchinfo);
    model.addAttribute("matchinfo", matchinfo);
    return "wait.html";
  }

  @GetMapping("wait/3")
  @Transactional
  public String wait(@RequestParam Integer user2, Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.room.addUser(loginUser);
    model.addAttribute("login_user", loginUser);
    User user = userMapper.selectByname(loginUser);
    int user1 = user.getId();
    model.addAttribute("pa", loginUser);
    String user1Hand = "Pa";
    User users = userMapper.selectById(user2);
    model.addAttribute("users", users);
    MatchInfo matchinfo = new MatchInfo();
    matchinfo.setUser1(user1);
    matchinfo.setUser2(user2);
    matchinfo.setUser1Hand(user1Hand);
    matchinfo.setisActive(true);
    matchInfoMapper.insertMatchInfo(matchinfo);
    model.addAttribute("matchinfo", matchinfo);
    return "wait.html";
  }

  /*
   * @GetMapping("gu") public String gu(Principal prin, ModelMap model) { String
   * loginUser = prin.getName(); this.room.addUser(loginUser);
   * model.addAttribute("login_user", loginUser); model.addAttribute("gu",
   * loginUser); model.addAttribute("room", this.room);
   *
   * return "lec02.html"; }
   *
   * @GetMapping("ch") public String ch(Principal prin, ModelMap model) { String
   * loginUser = prin.getName(); this.room.addUser(loginUser);
   * model.addAttribute("login_user", loginUser); model.addAttribute("ch",
   * loginUser); model.addAttribute("room", this.room);
   *
   * return "lec02.html"; }
   *
   * @GetMapping("pa") public String pa(Principal prin, ModelMap model) { String
   * loginUser = prin.getName(); this.room.addUser(loginUser);
   * model.addAttribute("login_user", loginUser); model.addAttribute("pa",
   * loginUser); model.addAttribute("room", this.room);
   *
   * return "lec02.html"; }
   */

}
