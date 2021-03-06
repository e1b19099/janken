package oit.is.z0413.kaizi.janken.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z0413.kaizi.janken.model.User;
import oit.is.z0413.kaizi.janken.model.UserMapper;
import oit.is.z0413.kaizi.janken.model.Match;
import oit.is.z0413.kaizi.janken.model.MatchMapper;
import oit.is.z0413.kaizi.janken.model.MatchInfo;
import oit.is.z0413.kaizi.janken.model.MatchInfoMapper;

@Service
public class AsyncKekka {
  boolean dbUpdated = false;

  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchInfoMapper miMapper;

  @Autowired
  MatchMapper mMapper;

  public ArrayList<Match> syncShowTMatchList() {
    return mMapper.selectAllByisActive();
  }

  public ArrayList<MatchInfo> syncShowMatchInfoList() {
    return miMapper.selectAll();
  }

  /**
   *
   * @param mi
   * @return
   */

  @Transactional
  public void setMatchInfo(String loginUser, Integer user2, String hand) {
    MatchInfo matchInfo = new MatchInfo();
    User user = userMapper.selectByname(loginUser);
    int user1 = user.getId();
    String user1Hand = hand;
    MatchInfo matchinfo2 = new MatchInfo();
    matchinfo2.setUser1(user1);
    matchinfo2.setUser2(user2);
    matchinfo2.setUser1Hand(user1Hand);
    matchinfo2.setisActive(true);
    miMapper.insertMatchInfo(matchinfo2);
    seachMatch(matchInfo);
  }

  @Transactional
  public int seachMatch(MatchInfo mi) {
    ArrayList<MatchInfo> allmi = miMapper.selectByisActive(true);
    MatchInfo matchInfo = new MatchInfo();
    int f = 0, id = 0;
    for (MatchInfo mi2 : allmi) {
      if (mi.getUser1() == mi2.getUser2() && mi.getUser2() == mi2.getUser1()) {
        matchInfo = mi2;
        f = 1;

      }
    }
    // ???????????????MatchInfo???????????????

    if (f == 0) {
      miMapper.insertMatchInfo(mi);
    } else {
      // ???????????????MatchInfo????????????
      miMapper.updateFById(matchInfo.getId());
      Match match = new Match();
      match.setUser1(matchInfo.getUser1());
      match.setUser2(matchInfo.getUser2());
      match.setUser1Hand(matchInfo.getUser1Hand());
      match.setUser2Hand(mi.getUser1Hand());
      match.setisActive(true);
      this.dbUpdated = true;
      mMapper.insertMatch(match);
      id = match.getId();
    }

    return id;

  }

  /**
   * dbUpdated???true??????????????????????????????DB??????????????????????????????????????????????????????
   *
   * @param emitter
   */
  @Async
  public void asyncShowMatchList(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (true) {// ???????????????
        // DB?????????????????????????????????0.5s??????
        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }
        // DB???????????????????????????????????????????????????????????????send??????1s?????????dbUpdated???false?????????
        ArrayList<Match> match = this.syncShowTMatchList();
        emitter.send(match);
        TimeUnit.MILLISECONDS.sleep(1000);
        dbUpdated = false;
        mMapper.updateAllF();
      }
    } catch (Exception e) {
      // ???????????????????????????????????????????????????
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncShowMatchList complete");
  }
}
