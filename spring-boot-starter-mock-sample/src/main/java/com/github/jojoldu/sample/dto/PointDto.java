package com.github.jojoldu.sample.dto;

import com.github.jojoldu.sample.domain.Point;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 16.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@Setter
@NoArgsConstructor
public class PointDto {

    private Long userId;
    private Long savePoint;
    private String description;

    @Builder
    public PointDto(Long userId, Long savePoint, String description) {
        this.userId = userId;
        this.savePoint = savePoint;
        this.description = description;
    }

    public Point toEntity() {
        return Point.builder()
                .userId(userId)
                .point(savePoint)
                .description(description)
                .build();
    }
}
