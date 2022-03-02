package software.amazon.forge.r.codegen.traits;

import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.AnnotationTrait;

public final class PointerTrait extends AnnotationTrait {
    public static final ShapeId ID = ShapeId.from("forge.crt#pointer");

    public PointerTrait(ObjectNode node) {
        super(ID, node);
    }

    public static final class Provider extends AnnotationTrait.Provider<PointerTrait> {
        public Provider() {
            super(ID, PointerTrait::new);
        }
    }
}