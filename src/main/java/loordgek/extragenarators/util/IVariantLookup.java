package loordgek.extragenarators.util;

public interface IVariantLookup {
  default String[] variantnames(){
      return new String[]{"inventory"};
  }
}
