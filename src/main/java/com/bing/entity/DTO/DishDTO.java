package com.bing.entity.DTO;

import com.bing.entity.DishDO;
import com.bing.entity.DishFlavorDO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 融合 DishDO 和 DishFlavorDO
 *
 * @author makejava
 * @since 2022-09-26 22:23:52
 */
@Data
public class DishDTO extends DishDO {

    private List<DishFlavorDO> flavors = new ArrayList<>();
    private String categoryName;
    private Integer copies;
}

