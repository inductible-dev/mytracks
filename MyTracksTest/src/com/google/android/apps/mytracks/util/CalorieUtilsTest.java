/*
 * Copyright 2013 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.android.apps.mytracks.util;

import android.location.Location;
import android.location.LocationManager;

import junit.framework.TestCase;

/**
 * A unit test for {@link CalorieUtils}.
 * 
 * @author youtaol
 */
public class CalorieUtilsTest extends TestCase {

  Location start = new Location(LocationManager.GPS_PROVIDER);
  Location stop = new Location(LocationManager.GPS_PROVIDER);
  private double grade = 10.0;
  private int weight = 20;
  private final long TIME_INTERVAL = 1000l;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    // Make the time interval is not 0
    stop.setTime(start.getTime() + TIME_INTERVAL);
  }

  /**
   * Checks using foot calculation equation.
   */
  public void testGetCalories_foot() {
    double actual = CalorieUtils.getCalories(start, stop, grade, weight,
        CalorieUtils.ActivityType.FOOT);
    double expected = CalorieUtils.calculateExpenditureFoot(start, stop, grade, weight);
    assertEquals(expected, actual);
  }

  /**
   * Checks whether using foot calculation equation while grade is negative.
   */
  public void testGetCalories_footNegativeGrade() {
    double actualGrade = -5;
    double expectGrade = 0;
    double actual = CalorieUtils.getCalories(start, stop, actualGrade, weight,
        CalorieUtils.ActivityType.FOOT);
    double expected = CalorieUtils.calculateExpenditureFoot(start, stop, expectGrade, weight);
    assertEquals(expected, actual);
  }

  /**
   * Checks using cycling calculation equation.
   */
  public void testGetCalories_cycling() {
    double actual = CalorieUtils.getCalories(start, stop, grade, weight,
        CalorieUtils.ActivityType.CYCLING);
    double expected = CalorieUtils.calculateExpenditureCycling(start, stop, grade, weight);
    assertEquals(expected, actual);
  }

  /**
   * Checks using running VO2 equation.
   */
  public void testGetVO2_running() {
    double actual = CalorieUtils.getVo2(CalorieUtils.CRTICAL_SPEED_RUNNING * 2, grade);
    double expected = CalorieUtils.calculateRunningVo2(CalorieUtils.CRTICAL_SPEED_RUNNING * 2,
        grade);
    assertEquals(expected, actual);
  }

  /**
   * Checks using walking VO2 equation.
   */
  public void testGetVO2_walking() {
    // Test at half the critical speed
    double footSpeed = CalorieUtils.CRTICAL_SPEED_RUNNING / 2.0;

    double actual = CalorieUtils.getVo2(footSpeed, grade);
    double expected = CalorieUtils.calculateWalkingVo2(footSpeed, grade);
    assertEquals(expected, actual);
  }

}