package com.derongan.minecraft.looty;

import com.derongan.minecraft.looty.item.ActionTarget;
import com.derongan.minecraft.looty.item.components.TargetComponent;
import com.derongan.minecraft.looty.item.handling.ItemRegistrarImpl;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ItemConstructorTestU {
    @Test
    public void testComponentRepresent() throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        ItemConstructor itemConstructor = new ItemConstructor();

        Yaml yaml = new Yaml(new ItemRepresenter());

        ItemRegistrarImpl registrar = new ItemRegistrarImpl();
        new TestItems().registerAllTests(registrar);

        registrar.getAllTypes().forEach(a -> {
            try {
                yaml.dump(a, new FileWriter("test/"+a.getName() + ".yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

//        String blaze = yaml.dump(TestItems.createDogFighter());
//
//        Map<String, Object> map = yaml.load(blaze);
//
//
//        System.out.println(blaze);
//        System.out.println("=========================");
//        String after = yaml.dump(itemConstructor.constructItemTypeFromMap(map));
//        System.out.println(after);

//        assertEquals(blaze, after);
    }

    @Test
    public void reflectionWhy() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = TargetComponent.class.getMethod("create", ActionTarget[].class);

        ActionTarget[] actionTargets = new ActionTarget[]{ActionTarget.ENTITY};


        method.invoke(null, (Object) actionTargets);

    }
}