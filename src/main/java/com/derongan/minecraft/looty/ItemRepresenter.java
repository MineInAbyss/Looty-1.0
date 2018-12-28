package com.derongan.minecraft.looty;

import com.badlogic.ashley.core.Component;
import com.derongan.minecraft.looty.item.ItemAction;
import com.derongan.minecraft.looty.item.ItemActionTarget;
import com.derongan.minecraft.looty.item.ItemType;
import com.derongan.minecraft.looty.item.components.ComponentFactory;
import com.derongan.minecraft.looty.item.handling.ActionTrigger;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

import java.util.*;
import java.util.stream.Collectors;

public class ItemRepresenter extends Representer {
    public ItemRepresenter() {
        super();

        multiRepresenters.put(ItemType.class, new ItemTypeRepresent());
        multiRepresenters.put(Enum.class, new NameEnumRepresent());
        multiRepresenters.put(Set.class, new SetAsListRepresent());

        getPropertyUtils().setAllowReadOnlyProperties(true);
    }

    private class ItemTypeRepresent implements Represent {
        @Override
        public Node representData(Object data) {
            ItemType itemType = (ItemType) data;

            List<NodeTuple> nodeTuples = new ArrayList<>();

            nodeTuples.add(keyValueNode("name", itemType.getName()));
            nodeTuples.add(keyValueNode("lore", itemType.getLore()));
            nodeTuples.add(keyValueNode("rarity", itemType.getRarity()));
            nodeTuples.add(keyValueNode("material", itemType.getMaterial()));
            nodeTuples.add(keyValueNode("durability", itemType.getDurability()));

            nodeTuples.add(new NodeTuple(representScalar(Tag.STR, "events"), getNodeForEvents(itemType)));

            return new MappingNode(Tag.MAP, nodeTuples, DumperOptions.FlowStyle.BLOCK);
        }
    }

    private Node getNodeForEvents(ItemType itemType) {
        List<Node> eventsNodeList = new ArrayList<>();

        for (ActionTrigger trigger : ActionTrigger.values()) {
            Collection<ItemAction> actions = itemType.getActions(trigger);

            Multimap<Set<ItemActionTarget>, ItemAction> itemActionGroups = HashMultimap.create();

            actions.forEach(action -> itemActionGroups.put(action.getTargets(), action));

            itemActionGroups.asMap().forEach((targets, items) -> {
                List<NodeTuple> eventForTriggerNodes = new ArrayList<>();
                eventForTriggerNodes.add(keyValueNode("trigger", trigger));
                eventForTriggerNodes.add(keyValueNode("target", targets));

                List<Node> actionsForTriggerNodes = items.stream()
                        .map(action -> getMappingNodeForComponentFactories(action.getComponentFactories()))
                        .collect(Collectors.toList());

                eventForTriggerNodes.add(new NodeTuple(
                        representScalar(Tag.STR, "actions"),
                        new SequenceNode(Tag.SEQ, actionsForTriggerNodes, DumperOptions.FlowStyle.BLOCK)
                ));

                eventsNodeList.add(new MappingNode(Tag.MAP, eventForTriggerNodes, DumperOptions.FlowStyle.BLOCK));
            });
        }

        return new SequenceNode(Tag.SEQ, eventsNodeList, DumperOptions.FlowStyle.BLOCK);
    }

    private NodeTuple keyValueNode(String key, Object value) {
        return new NodeTuple(representScalar(Tag.STR, key), represent(value));
    }

    private Node getMappingNodeForComponentFactories(Collection<ComponentFactory> componentFactories) {
        return new MappingNode(Tag.MAP, componentFactories.stream().map(this::getTupleForComponentFactory).collect(Collectors.toList()), DumperOptions.FlowStyle.BLOCK);
    }

    private NodeTuple getTupleForComponentFactory(ComponentFactory factory) {
        // First create a component
        Component component = factory.createComponent();

        // Get the public/bean properties
        Set<Property> properties = getProperties(component.getClass());

        // Get the name for this component
        String simpleName = component.getClass().getSimpleName();
        String componentKey = simpleName.substring(0, simpleName.lastIndexOf("Component"));

        componentKey = componentKey.replaceAll("(.)([A-Z])", "$1_$2").toLowerCase();


        Node keyNode = representScalar(Tag.STR, componentKey);

        Node valueNode;

        if (properties.isEmpty()) {
            // Flag style
            valueNode = representScalar(Tag.BOOL, "yes");
        } else if (properties.size() == 1) {
            // Single value style
            Property property = properties.stream().findAny().get();

            Object value = property.get(component);

            valueNode = represent(value);
        } else {
            Map<String, Object> values = new HashMap<>();

            properties.forEach(a->{
                values.put(a.getName(), a.get(component));
            });

            valueNode = representMapping(Tag.MAP, values, DumperOptions.FlowStyle.BLOCK);
        }

        return new NodeTuple(keyNode, valueNode);
    }

    private class NameEnumRepresent implements Represent {
        @Override
        public Node representData(Object data) {
            return representScalar(Tag.STR, ((Enum) data).name());
        }
    }

    private class SetAsListRepresent implements Represent {
        @Override
        public Node representData(Object data) {
            return representSequence(Tag.SEQ, (Iterable) data, DumperOptions.FlowStyle.FLOW);
        }
    }
}
