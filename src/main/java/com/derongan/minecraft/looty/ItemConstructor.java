package com.derongan.minecraft.looty;

import com.badlogic.ashley.core.Component;
import com.derongan.minecraft.looty.item.ActionBuilder;
import com.derongan.minecraft.looty.item.ItemActionTarget;
import com.derongan.minecraft.looty.item.ItemType;
import com.derongan.minecraft.looty.item.ItemTypeBuilder;
import com.derongan.minecraft.looty.item.components.ComponentFactory;
import com.derongan.minecraft.looty.item.handling.ActionTrigger;
import com.derongan.minecraft.looty.item.handling.ItemRarity;
import org.bukkit.Material;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ItemConstructor {

    public ItemType constructItemTypeFromMap(Map<String, Object> map) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        ItemTypeBuilder builder = new ItemTypeBuilder();

        builder.setName((String) map.get("name"));
        builder.setLore((List<String>) map.get("lore"));
        builder.setRarity(ItemRarity.valueOf((String) map.get("rarity")));
        builder.setMaterial(Material.valueOf((String) map.get("material")));
        builder.setDurability((Number) map.get("durability"));


        List<Map<String, Object>> events = (List<Map<String, Object>>) map.get("events");

        for (Map<String, Object> event : events) {
            ActionTrigger actionTrigger = ActionTrigger.valueOf(((String) event.get("trigger")).toUpperCase());

            Set<ItemActionTarget> targets = ((List<String>) event.get("target")).stream().map(a -> ItemActionTarget.valueOf(a.toUpperCase())).collect(Collectors.toSet());


            List<Map<String, Object>> eventActions = (List<Map<String, Object>>) event.get("actions");

            for (Map<String, Object> actions : eventActions) {
                ActionBuilder actionBuilder = ActionBuilder.create()
                        .setTargets((ItemActionTarget[]) targets.toArray(new ItemActionTarget[0]));


                actions.forEach((k, v) -> {
                    getComponentFactoryReflectively(k, v).ifPresent(factory -> {
                        actionBuilder.add(factory);
                    });
                });

                builder.addEntityAction(actionBuilder.build(), actionTrigger);
            }
        }
        return builder.build();
    }

    private static Optional<ComponentFactory> getComponentFactoryReflectively(String name, Object args) {
        try {
            Class<Component> componentClass = mapToComponent(name);
            Method createMethod = Arrays.stream(componentClass.getMethods()).filter(a -> a.getName().equals("create")).findFirst().get();

            Class[] parameterTypes = createMethod.getParameterTypes();

            if (args instanceof Map) {
                //TODO can we just assume that the values method is ordered as we like? No but we will for now anyway :).
                Collection<Object> argsCol = ((Map) args).values();
                Object[] params = createParams(parameterTypes, new ArrayList<>(argsCol));
                return Optional.of((ComponentFactory) createMethod.invoke(null, params));
            } else {
                return Optional.of((ComponentFactory) createMethod.invoke(null, createParams(parameterTypes, Collections.singletonList(args))));
            }
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.err.println(String.format("Could not create component %s. Skipping", name));
            System.err.println(e);
        }
        return Optional.empty();
    }


    private static Object[] createParams(Class[] types, List<Object> args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] parsedArgs = new Object[types.length];

        for (int i = 0; i < types.length; i++) {
            Class type = types[i];
            Object arg = args.get(i);

            if (type.isArray()) {
                List listArg = (List) arg;

                Object vargs = Array.newInstance(type.getComponentType(), listArg.size());

                for (int i1 = 0; i1 < listArg.size(); i1++) {
                    if (type.getComponentType().isEnum()) {
                        Array.set(vargs, i, type.getComponentType().getMethod("valueOf", String.class).invoke(null, (String) listArg.get(i1)));
                    } else {
                        if (!type.getComponentType().isPrimitive())
                            Array.set(vargs, i, type.cast(listArg.get(i1)));
                        else
                            Array.set(vargs, i, listArg.get(i1));
                    }
                }

                parsedArgs[i] = vargs;
            } else {
                if (type.isEnum()) {
                    parsedArgs[i] = type.getMethod("valueOf", String.class).invoke(null, (String) arg);
                } else {
                    if (!type.isPrimitive())
                        parsedArgs[i] = type.cast(arg);
                    else
                        parsedArgs[i] = arg;
                }
            }
        }

        return parsedArgs;
    }

    private static Class<Component> mapToComponent(String object) throws ClassNotFoundException {
        object = Arrays.stream(object.split("_")).map(a -> a.substring(0, 1).toUpperCase() + a.substring(1)).collect(Collectors.joining());
        return (Class<Component>) Class.forName("com.derongan.minecraft.looty.item.components." + object + "Component");
    }
}
