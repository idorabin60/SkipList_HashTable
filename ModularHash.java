import java.util.Random;

public class ModularHash implements HashFactory<Integer> {
    private Random rand;
    private HashingUtils utils;

    public ModularHash() {
        this.rand = new Random();
        this.utils = new HashingUtils();

    }

    @Override
    public HashFunctor<Integer> pickHash(int k) {
        Functor blaFunctor = new Functor(k);
        return blaFunctor;
    }

    public class Functor implements HashFunctor<Integer> {
        final private int a;
        final private int b;
        final private long p;
        final private int m;

        public Functor(int k) {
            this.a = rand.nextInt(Integer.MAX_VALUE - 1) + 1;
            this.b = rand.nextInt(Integer.MAX_VALUE);
            long potential = utils.genLong(Integer.MAX_VALUE + 1, Long.MAX_VALUE - 1);
            while (!utils.runMillerRabinTest(potential, 5)) {
                potential = utils.genLong(Integer.MAX_VALUE + 1, Long.MAX_VALUE - 1);
            }
            this.p = potential;
            this.m = (int) utils.fastModularPower(2, k, Long.MAX_VALUE);
            System.out.println(m);

        }

        @Override
        public int hash(Integer key) {
            long y = utils.mod((this.a * key) + this.b, this.p);
            int resault = (int) utils.mod(y, m);
            return resault;
        }

        public int a() {
            return a;
        }

        public int b() {
            return b;
        }

        public long p() {
            return p;
        }

        public int m() {
            return m;
        }

    }
}
