package com.example.testwx.bean;

import com.example.testwx.bean.base.AbstractButton;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class MenuButton extends AbstractButton {
    private List<AbstractButton> sub_button=new ArrayList<>();

    public MenuButton(String name, List<AbstractButton> sub_button) {
        super(name);
        this.sub_button = sub_button;
    }
}
