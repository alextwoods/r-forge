package software.amazon.forge.r.codegen.traits;

import software.amazon.smithy.model.SourceLocation;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.StringTrait;

public final class CType extends StringTrait {
    public static final ShapeId ID = ShapeId.from("forge.crt#ctype");

    public CType(String value, SourceLocation sourceLocation) {
        super(ID, value, sourceLocation);
    }

    public CType(String value) {
        this(value, SourceLocation.NONE);
    }

    public static final class Provider extends StringTrait.Provider<CType> {
        public Provider() {
            super(ID, CType::new);
        }
    }
}
