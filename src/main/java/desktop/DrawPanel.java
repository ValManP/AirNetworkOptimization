package desktop;

import Jama.Matrix;
import impl.controllers.BusinessController;
import impl.controllers.MatrixController;
import impl.controllers.NetworkController;
import impl.entities.Aircraft;
import impl.entities.City;
import impl.entities.Company;
import impl.entities.Network;
import impl.entities.Route;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class DrawPanel extends JPanel implements Runnable, MouseInputListener {
    public static final int PANEL_SIZE = 460;
    
    private final static NetworkController networkController = new NetworkController();
    private final static BusinessController businessController = new BusinessController();
    private final static MatrixController matrixController = new MatrixController();
    
    private final static int radius = 5;
    private final static int diff = 10;
    
    private DesktopFrame desktopPanel;
    private Company company;
    private Network network;
    private Image mapImg;
    
    private Point firstCoordinate;

    public void setMapImg(Image mapImg) {
        this.mapImg = mapImg;
    }
    
    public DrawPanel(DesktopFrame desktopPanel, Network network, Company company) {
        super();
        this.desktopPanel = desktopPanel;
        this.network = network;
        this.company = company;
        addMouseListener(this);
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                    Thread.sleep(5);
            } catch (InterruptedException ex) {}
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(mapImg, 0, 0, this.getWidth(), this.getHeight(), null);

        g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        List<City> cities = network.getCities();
        Matrix matrix = network.getAdjacencyMatrix();

        g2d.setColor(Color.red);
        cities.forEach((city) -> {
            g2d.fillOval((int)city.getX() - radius, (int)city.getY() - radius, 2 * radius, 2 * radius);
        });

        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                if (matrix.get(i, j) > 0) {
                    City cityA = cities.get(i);
                    City cityB = cities.get(j);
                    g2d.setColor(Color.red);
                    g2d.drawLine((int) cityA.getX(), (int) cityA.getY(), (int) cityB.getX(), (int) cityB.getY());
                }
            }
        }
    }

    @Override
    public void mouseClicked( MouseEvent e ) {
        if (desktopPanel.isAddCityMode()) {
            City city = businessController.createCity("Some name", e.getX(), e.getY());
            networkController.addCity(network, city);
            desktopPanel.log("Actual Estrada Coeff = " + matrixController.calculateEstradaCoeff(network));
        }
        repaint();
    }
    
    private City getClosestCity(int x, int y) {
        List<City> cities = network.getCities();
        for (City city: cities) {
            int diffX = Math.abs((int) city.getX() - x);
            int diffY = Math.abs((int) city.getY() - y);
            if (diffX < diff & diffY < diff) {
                return city;
            }
        }
        return null;
    }

    // Unused Mouse Listener Methods
    @Override
    public void mousePressed( MouseEvent e ) {
        if (desktopPanel.isAddRouteMode()) {
            firstCoordinate = new Point(e.getX(), e.getY());
        }
    }
    @Override
    public void mouseMoved( MouseEvent e ) {}
    @Override
    public void mouseReleased( MouseEvent e ) {
        if (desktopPanel.isAddRouteMode()) {
            Aircraft aircraft = businessController.createAircraft(company, 100, 1);
            City cityA = getClosestCity(firstCoordinate.x, firstCoordinate.y);
            City cityB = getClosestCity(e.getX(), e.getY());
            if (cityA != null & cityB != null & cityA != cityB) {
                Route route = businessController.createRoute(cityA, cityB, aircraft);
                networkController.addRoute(network, route, true);
                desktopPanel.log("Actual Estrada Coeff = " + matrixController.calculateEstradaCoeff(network));
            }
        }
        repaint();
    }
    @Override
    public void mouseEntered( MouseEvent e ) {}
    @Override
    public void mouseExited( MouseEvent e ) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
}