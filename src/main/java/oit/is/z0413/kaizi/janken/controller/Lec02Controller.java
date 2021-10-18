package oit.is.z0413.kaizi.janken.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/lec02")
public class Lec02Controller {

  /**
   * @param name
   * @return
   */

  @GetMapping("/lec02")
  public String lec02() {
    return "lec02.html";
  }

  @PostMapping("/lec02/{name}")
  public String lec02(@RequestParam String name, ModelMap model) {
    model.addAttribute("name", name);
    return "lec02.html";
  }

  @PostMapping("/gu")
  public String gu(ModelMap model) {
    String name = "gu";
    model.addAttribute("gu", name);
    return "lec02.html";
  }

  @PostMapping("/choki")
  public String choki(ModelMap model) {
    String name = "gu";
    model.addAttribute("choki", name);
    return "lec02.html";
  }

  @PostMapping("/pa")
  public String pa(ModelMap model) {
    String name = "gu";
    model.addAttribute("pa", name);
    return "lec02.html";
  }

}
