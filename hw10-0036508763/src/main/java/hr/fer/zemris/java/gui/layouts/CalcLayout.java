package hr.fer.zemris.java.gui.layouts;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * CalcLayout defines layout which will be used in a calculator.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class CalcLayout implements LayoutManager2 {

    /**
     * Spacing between two tiles.
     */
    private int spacing;
    /**
     * Map of components.
     */
    private Map<RCPosition, Component> components;


    /**
     * Constructor.
     *
     * @param spacing Spacing.
     */
    public CalcLayout(int spacing) {
        this.spacing = spacing;
        this.components = new HashMap<>();
    }

    /**
     * Constructor which sets spacing to 0.
     */
    public CalcLayout() {
        this(0);
    }

    /**
     * {@inheritDoc}
     * @param comp
     * @param constraints
     */
    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        RCPosition position = null;
        if (constraints instanceof String) {
            position = parseRCPosition((String) constraints);
        } else if (!(constraints instanceof RCPosition) ||
                invalidConstraints((RCPosition) constraints)) {
            throw new UnsupportedOperationException();
        } else {
            components.forEach((k, v) -> {
                RCPosition rcPosition = (RCPosition) constraints;
                if (k.getColumn() == rcPosition.getColumn() &&
                        k.getRow() == rcPosition.getRow()) {
                    if (v != comp) {
                        throw new UnsupportedOperationException();
                    }
                }
            });
        }
        if (position == null) {
            position = (RCPosition) constraints;
        }
        components.put(position, comp);
    }

    /**
     * {@inheritDoc}
     * @param target
     * @return
     */
    @Override
    public Dimension maximumLayoutSize(Container target) {
        return getSize(target, Component::getMaximumSize);
    }

    /**
     * {@inheritDoc}
     * @param target
     * @return
     */
    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    /**
     * {@inheritDoc}
     * @param target
     * @return
     */
    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    /**
     * {@inheritDoc}
     * @param target
     */
    @Override
    public void invalidateLayout(Container target) {
    }

    /**
     * {@inheritDoc}
     * @param name
     * @param comp
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     * @param comp
     */
    @Override
    public void removeLayoutComponent(Component comp) {
        RCPosition keyToBeRemoved = null;
        for (Map.Entry<RCPosition, Component> entry : components.entrySet()) {
            if (entry.getValue() == comp) {
                keyToBeRemoved = entry.getKey();
                break;
            }
        }
        components.remove(keyToBeRemoved);
    }

    /**
     * {@inheritDoc}
     *
     * @param parent
     * @return
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return getSize(parent, Component::getPreferredSize);
    }

    /**
     * {@inheritDoc}
     * @param parent
     * @return
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return getSize(parent, Component::getMinimumSize);
    }

    /**
     * {@inheritDoc}
     * @param parent
     */
    @Override
    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        int widthOfAContainer = parent.getWidth() - (insets.left + insets.right);
        int heightOfAContainer = parent.getHeight() - (insets.top + insets.bottom);

        int widthOfAComponent = (int) Math.round(((double) widthOfAContainer - (6 * spacing)) / 7);
        int heightOfAComponent = (int) Math.round(((double) heightOfAContainer - (4 * spacing)) / 5);

        int nOfUniformColumns = widthOfAComponent * 7;
        int nOfUniformRows = heightOfAComponent * 5;

        int nOfDifferentColumns = Math.abs(nOfUniformColumns - widthOfAContainer + (6 * spacing));
        int nOfDifferentRows = Math.abs(nOfUniformRows - heightOfAContainer + (4 * spacing));

        int[] columnsThatNeedShrinking = new int[8];
        int[] rowsThatNeedShrinking = new int[6];

        boolean shrinkColumns = (((double) widthOfAContainer - (6 * spacing)) / 7) < widthOfAComponent;
        boolean shrinkRows = (((double) heightOfAContainer - (4 * spacing)) / 5) < heightOfAComponent;

        extractDifferent(nOfDifferentColumns, columnsThatNeedShrinking, shrinkColumns);

        extractDifferent(nOfDifferentRows, rowsThatNeedShrinking, shrinkRows);

        components.forEach((k, v) -> {
            int xCoordinate;
            int yCoordinate;
            int currentWidth;
            int currentHeight;
            int spacing = this.spacing;

            currentWidth = widthOfAComponent + columnsThatNeedShrinking[k.getColumn()];
            currentHeight = heightOfAComponent + rowsThatNeedShrinking[k.getRow()];


            if (k.getRow() == 1) {
                yCoordinate = 0;
                int widthOfAFirstComponent = 5 * widthOfAComponent + 4 * spacing;
                if (k.getColumn() == 1) {
                    xCoordinate = 0;
                    currentWidth = widthOfAFirstComponent + countShrunkColumns(columnsThatNeedShrinking, 5);
                } else {
                    xCoordinate = widthOfAFirstComponent + (k.getColumn() - 6) * widthOfAComponent + spacing * (k.getColumn() - 5);
                }
            } else if (k.getColumn() == 1) {
                xCoordinate = 0;
                yCoordinate = (heightOfAComponent + spacing) * (k.getRow() - 1);
            } else {
                xCoordinate = (widthOfAComponent + spacing) * (k.getColumn() - 1);
                yCoordinate = (heightOfAComponent + spacing) * (k.getRow() - 1);
            }

            xCoordinate += countShrunkColumns(columnsThatNeedShrinking, k.getColumn() - 1);
            yCoordinate += countShrunkColumns(rowsThatNeedShrinking, k.getRow() - 1);

            v.setBounds(xCoordinate, yCoordinate, currentWidth, currentHeight);
        });
    }

    /**
     * Method that fills given array with -1 if flag shrinkColumns is true; otherwise fills array with 1.
     * Data is filled for nOfDifferentColumns times.
     * Even indexes are filled first.
     * @param nOfDifferentColumns Number of 1 or -1
     * @param columnsThatNeedShrinking Array in which this data will be writteon to.
     * @param shrinkColumns Flag that indicates whether the 1 or -1 will be written.
     */
    private void extractDifferent(int nOfDifferentColumns, int[] columnsThatNeedShrinking, boolean shrinkColumns) {
        for (int i = 2; i < columnsThatNeedShrinking.length; i += 2) {
            if (nOfDifferentColumns <= 0) {
                break;
            }
            columnsThatNeedShrinking[i] = shrinkColumns ? -1 : 1;
            nOfDifferentColumns--;
        }

        for (int i = 1; i < columnsThatNeedShrinking.length; i += 2) {
            if (nOfDifferentColumns <= 0) {
                break;
            }
            columnsThatNeedShrinking[i] = shrinkColumns ? -1 : 1;
            nOfDifferentColumns--;
        }
    }

    /**
     * Counts the number of shrunk columns or rows until given value.
     * @param columns Array which will be used in counting.
     * @param until Counts until given value + 1.
     * @return Returns count of shrunk columns or rows.
     */
    private int countShrunkColumns(int[] columns, int until) {
        int count = 0;
        boolean shrinking = true;
        for (int i = 1; i < until + 1; i++) {
            if (columns[i] == 1) {
                shrinking = false;
            }
            if (columns[i] == 1 || columns[i] == -1) {
                count++;
            }
        }
        return shrinking ? -1 * count : count;
    }

    /**
     * Checks constraints for a given value.
     * @param rcPosition position in a grid
     * @return Returns true if constraints are invalid; otherwise return false.
     */
    private boolean invalidConstraints(RCPosition rcPosition) {
        if (rcPosition.getColumn() < 1 || rcPosition.getColumn() > 7 ||
                rcPosition.getRow() < 1 || rcPosition.getRow() > 5) {
            return true;
        }
        if (rcPosition.getRow() == 1 && rcPosition.getColumn() < 6 && rcPosition.getColumn() > 1) {
            return true;
        }
        return false;
    }

    /**
     * Get size specified by given function of this layout.
     * @param parent Container to which
     * @param function Function which gets proper size.
     * @return Returns Dimension.
     */
    private Dimension getSize(Container parent, Function<Component, Dimension> function) {
        int height = 0;
        int width = 0;

        for (Component component : parent.getComponents()) {
            Dimension currentDimension = function.apply(component);
            if (currentDimension == null) {
                continue;
            }
            height = Math.max(height, currentDimension.height);
            width = Math.max(width, currentDimension.width);

            if (components.get(new RCPosition(1, 1)) == component) {
                width = (int) Math.round((double) (width - 4 * spacing) / 5);
            }
        }

        height *= 5;
        width *= 7;

        Insets insets = parent.getInsets();

        height += 4 * spacing + insets.top + insets.bottom;
        width += 6 * spacing + insets.right + insets.left;
        return new Dimension(width, height);
    }

    /**
     * Parses given text to RCPosition.
     * @param text Text which will be parsed.
     * @return
     */
    private RCPosition parseRCPosition(String text) {
        String[] arguments = text.split(",");
        try {
            return new RCPosition(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]));
        } catch (Exception e) {
            throw new CalcLayoutException();
        }
    }
}
