package com.derongan.minecraft.looty;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

public class ItemRepresenterTestU {
    @Test
    public void testComponentRepresent() {
        ItemRepresenter itemRepresenter = new ItemRepresenter();

        Yaml yaml = new Yaml(itemRepresenter);


        System.out.println(yaml.dump(TestItems.createDogFighter()));
    }
}