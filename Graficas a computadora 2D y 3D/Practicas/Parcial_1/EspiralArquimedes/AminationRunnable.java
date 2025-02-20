package EspiralArquimedes;

class AnimationRunnable implements Runnable {
    private final EspiralPanel panel;
    private final long delay = 1; // Retardo en milisegundos entre cada actualización

    public AnimationRunnable(EspiralPanel panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        // Incrementa theta y repinta el panel hasta alcanzar el máximo
        while (panel.canIncrement()) {
            panel.incrementTheta();
            panel.repaint();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
