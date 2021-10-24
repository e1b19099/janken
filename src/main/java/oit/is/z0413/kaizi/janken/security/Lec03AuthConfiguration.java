package oit.is.z0413.kaizi.janken.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class Lec03AuthConfiguration extends WebSecurityConfigurerAdapter {
  /**
   * 認証処理に関する設定（誰がログインできるか）
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    // 平文のパスワードをエンコーダにかけてハッシュ化し，"user1"と関連付けている．ロール名は"USER"
    // プログラム中に素のパスワードが含まれることになるので望ましくない
    auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder().encode("p@ss")).roles("USER");
    auth.inMemoryAuthentication().withUser("user2").password(passwordEncoder().encode("1234")).roles("USER");

    // $ sshrun htpasswd -nbBC 10 admin adm1n
    // htpasswdでBCryptエンコードを行った後の文字列をパスワードとして指定している．
    auth.inMemoryAuthentication().withUser("admin")
        .password("$2y$10$3e7Hs2QZ/p91yJVgP5y/1OC7AC8jfc6YDYDzMGK1XieDlNR2nBGDe").roles("ADMIN");
    // $ sshrun htpasswd -nbBC 10 ほんだ hond@
    auth.inMemoryAuthentication().withUser("ほんだ")
        .password("$2y$10$CAJuUh/3RGRZwLXiYkzjCOB5pVIUqLbYVKHFPUMIW/cmAgxL7nLFi").roles("USER");
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 認可処理に関する設定（認証されたユーザがどこにアクセスできるか）
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // Spring Securityのフォームを利用してログインを行う
    http.formLogin();
    // http://localhost:8000/sample3 で始まるURLへのアクセスはログインが必要
    // antMatchers().authenticated がantMatchersへのアクセスに認証を行うことを示す
    // antMatchers()の他にanyRequest()と書くとあらゆるアクセス先を表現できる
    // authenticated()の代わりにpermitAll()と書くと認証処理が不要であることを示す
    http.authorizeRequests().antMatchers("/lec02").authenticated();
    // Spring Securityの機能を利用してログアウト．ログアウト時は http://localhost:8000/ に戻る
    http.logout().logoutSuccessUrl("/");

    /**
     * 以下2行はh2-consoleを利用するための設定なので，開発が完了したらコメントアウトすることが望ましい
     * CSRFがONになっているとフォームが対応していないためアクセスできない
     * HTTPヘッダのX-Frame-OptionsがDENYになるとiframeでlocalhostでのアプリが使えなくなるので，H2DBのWebクライアントのためだけにdisableにする必要がある
     */
    http.csrf().disable();
    http.headers().frameOptions().disable();
    
  }
}
