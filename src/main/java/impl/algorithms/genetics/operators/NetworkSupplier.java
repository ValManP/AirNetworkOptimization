package impl.algorithms.genetics.operators;

import impl.algorithms.genetics.entities.NetworkAllele;
import impl.entities.Network;

import java.util.Random;
import java.util.function.Supplier;

public class NetworkSupplier implements Supplier<NetworkAllele> {
    private double USING_PROBABILITY = 0.6;

    private Network network;

    public NetworkSupplier(Network network) {
        this.network = network;
    }

    @Override
    public NetworkAllele get() {
        Random random = new Random();
        boolean isUsed = false;
        int aircraftPosition = -1;

        int positionCount = network.getSize() * (network.getSize() - 1) / 2;

        double deviceProbability = random.nextDouble();
        if (deviceProbability < USING_PROBABILITY) {
            aircraftPosition = random.nextInt(positionCount);
            isUsed = true;
        }

        return new NetworkAllele(isUsed, aircraftPosition);
    }
}
