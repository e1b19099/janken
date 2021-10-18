package oit.is.z0413.kaizi.janken.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import oit.is.z0413.kaizi.janken.model.Entry;

/**
 * /sample3へのリクエストを扱うクラス authenticateの設定をしていれば， /sample3へのアクセスはすべて認証が必要になる
 */
@Controller
@RequestMapping("/")
public class Lec02Controller {

  @Autowired
  private Entry room;

  @GetMapping("/lec02")
  public String lec02(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.room.addUser(loginUser);
    model.addAttribute("login_user", loginUser);
    model.addAttribute("room", this.room);

    return "lec02.html";
  }

  @GetMapping("gu")
  public String gu(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.room.addUser(loginUser);
    model.addAttribute("login_user", loginUser);
    model.addAttribute("gu", loginUser);
    model.addAttribute("room", this.room);

    return "lec02.html";
  }

  @GetMapping("ch")
  public String ch(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.room.addUser(loginUser);
    model.addAttribute("login_user", loginUser);
    model.addAttribute("ch", loginUser);
    model.addAttribute("room", this.room);

    return "lec02.html";
  }

  @GetMapping("pa")
  public String pa(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.room.addUser(loginUser);
    model.addAttribute("login_user", loginUser);
    model.addAttribute("pa", loginUser);
    model.addAttribute("room", this.room);

    return "lec02.html";
  }

}
