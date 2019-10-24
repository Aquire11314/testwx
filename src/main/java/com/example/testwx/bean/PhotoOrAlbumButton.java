package com.example.testwx.bean;

import com.example.testwx.bean.base.AbstractButton;
import lombok.Data;

import java.util.List;

@Data
public class PhotoOrAlbumButton extends AbstractButton {
    private String type="pic_photo_or_album";
    private String key;
    private List<AbstractButton>sub_button;

    public PhotoOrAlbumButton(String name, String key, List<AbstractButton> sub_button) {
        super(name);
        this.key = key;
        this.sub_button = sub_button;
    }
}
