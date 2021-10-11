package oit.is.z0413.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Lec02Controller
 *
 * クラスの前に@Controllerをつけていると，HTTPリクエスト（GET/POSTなど）があったときに，このクラスが呼び出される
 */
@Controller
public class Lec02Controller {

  /**
   * パスパラメータ2つをGETで受け付ける 1つ目の変数をparam1という名前で，2つ目の変数をparam2という名前で受け取る
   * GETで受け取った2つの変数とsample22()の引数の名前が同じなため， 引数の前に @PathVariable と付けるだけで，パスパラメータの値を
   * javaで処理できるようになる ModelMapはthymeleafに渡すためのMapと呼ばれるデータ構造を持つ変数
   * Mapはkeyとvalueの組み合わせで値を保持する
   *
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
