package org.eclipse.persistence.internal.jaxb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.persistence.core.queries.CoreAttributeGroup;
import org.eclipse.persistence.jaxb.AttributeNode;
import org.eclipse.persistence.jaxb.ObjectGraph;
import org.eclipse.persistence.jaxb.SubGraph;

public class ObjectGraphImpl<X> extends AttributeNodeImpl<X> implements ObjectGraph<X>, SubGraph<X>{

    private CoreAttributeGroup attributeGroup;
    private Map<String, AttributeNode> attributeNodes;
    
    
    public ObjectGraphImpl(CoreAttributeGroup group) {
        super();
        this.attributeGroup = group;
        this.attributeNodes = new HashMap<String, AttributeNode>();
    }
    public <X> Class<X> getClassType() {
        return attributeGroup.getType();
    }

    public String getName() {
        return attributeGroup.getName();
    }

    public <X> void addAttributeNodes(String... attributeName) {
        for(String attribute:attributeName) {
            AttributeNodeImpl impl = new AttributeNodeImpl(attribute);
            this.attributeNodes.put(attribute, impl);
            attributeGroup.addAttribute(attribute);
        }
    }

    public <X> SubGraph<X> addSubGraph(String attribute) {
        CoreAttributeGroup group = new CoreAttributeGroup();
        if(attributeGroup.getItem(attribute) == null) {
            AttributeNodeImpl impl = new AttributeNodeImpl(attribute);
            this.attributeNodes.put(attribute,  impl);
        }
        this.attributeGroup.addAttribute(attribute, group);
        
        return new ObjectGraphImpl(group);
    }

    public <X> SubGraph<X> addSubGraph(String attribute, Class<X> type) {
        CoreAttributeGroup group = new CoreAttributeGroup(null, type, true);
        if(attributeGroup.getItem(attribute) == null) {
            AttributeNodeImpl impl = new AttributeNodeImpl(attribute);
            this.attributeNodes.put(attribute,  impl);
        }
        this.attributeGroup.addAttribute(attribute, group);
        
        return new ObjectGraphImpl(group);
    }

    public List<AttributeNode<?>> getAttributeNodes() {
        ArrayList<AttributeNode<?>> nodes = new ArrayList<AttributeNode<?>>();
        for(AttributeNode<?> next:this.attributeNodes.values()) {
            nodes.add(next);
        }
        return nodes;
    }
    
    public CoreAttributeGroup getAttributeGroup() {
        return this.attributeGroup;
    }

}
