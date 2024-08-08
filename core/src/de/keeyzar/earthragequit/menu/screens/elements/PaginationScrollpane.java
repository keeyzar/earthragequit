package de.keeyzar.earthragequit.menu.screens.elements;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

/**
 * @author = Keeyzar on 26.07.2015
 */
public class PaginationScrollpane extends ScrollPane {


    private boolean wasPanDragFling = false;
    public int setHorizontalPages;


    public PaginationScrollpane(Actor widget) {
        super(widget);
    }

    public PaginationScrollpane(Actor widget, Skin skin) {
        super(widget, skin);
        setFlingTime(0.1f);
        setFlickScrollTapSquareSize(50);
        


    }

    public PaginationScrollpane(Actor widget, Skin skin, String styleName) {
        super(widget, skin, styleName);
    }

    public PaginationScrollpane(Actor widget, ScrollPaneStyle style) {
        super(widget, style);
    }

    @Override
    public void act (float delta) {
        super.act(delta);


        if (wasPanDragFling && !isPanning() && !isDragging() && !isFlinging()) {
            wasPanDragFling = false;
            scrollToPage();
        } else {
            if (isPanning() || isDragging() || isFlinging()) {
                wasPanDragFling = true;
            }
        }
    }


    private void scrollToPage () {
        final float width = getWidth();
        final float scrollX = getScrollX();
        final float maxX = getMaxX();

        if (scrollX >= maxX || scrollX <= 0) return;
        Table table = (Table)getWidget();
        Array<Actor> pages = table.getChildren();
        float pageX = 0;
        float pageWidth = 0;
        if (pages.size > 0) {
            for (Actor a : pages) {
                pageX = a.getX();
                pageWidth = a.getWidth();
                if (scrollX < (pageX + pageWidth * 0.5)) {
                    break;
                }

            }

            float x = (MathUtils.clamp(pageX - (width - pageWidth) / 2, 0, maxX));
            scrollTo(x, 0, 1200, 800);
        }
    }








}
