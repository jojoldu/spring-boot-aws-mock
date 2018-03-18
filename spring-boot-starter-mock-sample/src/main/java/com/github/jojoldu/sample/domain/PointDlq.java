package com.github.jojoldu.sample.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 18.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@NoArgsConstructor
@Getter
@Entity
public class PointDlq {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Long point;
    private String description;

    @Builder
    public PointDlq(Long userId, Long point, String description) {
        this.userId = userId;
        this.point = point;
        this.description = description;
    }
}
