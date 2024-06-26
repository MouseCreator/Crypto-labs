package mouse.univ.algorithm.common;

public interface Permutation {
    BitArr applyPermutation(BitArr initial, int[] table);
    BitArr applyExtendedPermutation(BitArr initial, int[] table);
}
