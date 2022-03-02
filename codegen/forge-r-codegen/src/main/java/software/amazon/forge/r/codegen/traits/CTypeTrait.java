package software.amazon.forge.r.codegen.traits;

import software.amazon.smithy.model.SourceLocation;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.StringTrait;

public final class CTypeTrait extends StringTrait {
    public static final ShapeId ID = ShapeId.from("forge.crt#ctype");

    public CTypeTrait(String value, SourceLocation sourceLocation) {
        super(ID, value, sourceLocation);
    }

    public CTypeTrait(String value) {
        this(value, SourceLocation.NONE);
    }

    public static final class Provider extends StringTrait.Provider<CTypeTrait> {
        public Provider() {
            super(ID, CTypeTrait::new);
        }
    }
}
