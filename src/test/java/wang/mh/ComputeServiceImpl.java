package wang.mh;

public class ComputeServiceImpl implements ComputeService {

    @Override
    public int addOne(int i) {
        return ++i;
    }

}
