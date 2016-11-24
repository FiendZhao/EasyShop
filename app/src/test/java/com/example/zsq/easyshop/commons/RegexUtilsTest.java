package com.example.zsq.easyshop.commons;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ZSQ on 2016/11/24.
 */
public class RegexUtilsTest {
  public static final int VERIFY_SUCCESS = 0;

  public static final int VERIFY_LENGTH_ERROR = 1;

  public static final int VERIFY_TYPE_ERROR = 2;

  private RegexUtils regexUtils;
  @Before public void setUp() throws Exception {
    regexUtils = new RegexUtils();
  }

  @Test public void verifyUsername_long() throws Exception {
    int username = regexUtils.verifyUsername("123");
    Assert.assertEquals(1,username);
  }

  @Test public void verifyUsername_type() throws Exception {
    int username = regexUtils.verifyUsername("123_gg");
    Assert.assertEquals(2,username);
  }
  @Test public void verifyPassword() throws Exception {
    int password = regexUtils.verifyPassword("123456");
  }

  @Test public void verifyNickname() throws Exception {

  }
}