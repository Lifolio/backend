package com.example.lifolio.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Alarm")
public class Alarm {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "id")
 private Long id;

 @Column(name="user_id")
 private Long userId;

 @Column(name = "week_alarm")
 private Integer weekAlarm;

 @Column(name = "badge_alarm")
 private Integer badgeAlarm;

 @Column(name = "todo_alarm")
 private Integer todoAlarm;

 @Column(name = "goal_alarm")
 private Integer goalAlarm;

 @Column(name = "upload_alarm")
 private Integer uploadAlarm;

 @Column(name = "interest_alarm")
 private Integer interestAlarm;

 @Column(name = "like_alarm")
 private Integer likeAlarm;

 @Column(name = "marketing_alarm")
 private Integer marketingAlarm;


 public void updateAllAlarm(int weekAlarm, int badgeAlarm, int todoAlarm, int goalAlarm, int uploadAlarm, int interestAlarm, int likeAlarm, int marketingAlarm){
  this.weekAlarm = weekAlarm;
  this.badgeAlarm = badgeAlarm;
  this.todoAlarm = todoAlarm;
  this.goalAlarm = goalAlarm;
  this.uploadAlarm = uploadAlarm;
  this.interestAlarm = interestAlarm;
  this.likeAlarm = likeAlarm;
  this.marketingAlarm = marketingAlarm;
 }

 public void updateMyAllAlarm(int weekAlarm, int badgeAlarm) {
  this.weekAlarm = weekAlarm;
  this.badgeAlarm = badgeAlarm;
 }
}
