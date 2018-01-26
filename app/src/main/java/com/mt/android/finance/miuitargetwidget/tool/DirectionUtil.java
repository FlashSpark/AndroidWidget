package com.mt.android.finance.miuitargetwidget.tool;

/**
 * 方向工具
 * author:ps
 * date:2018/1/25
 */

public class DirectionUtil {

    /**
     * 方向常量类
     */
    public class Direction {
        public static final int UP = 0x01;
        public static final int DOWN = 0x02;
        public static final int LEFT = 0x03;
        public static final int RIGHT = 0x04;
        public static final int ARBITRARY = 0x05;
    }

    /**
     * 获取 (x1,y1) -> (x2, y2) 方向
     * 平面直角坐标系中,向量与x轴正向夹角与方向关系如下
     * UP:[45°,135°]
     * DOWN:[215°,315°]
     * LEFT:(135°,215°)
     * RIGHT:[315°,360°) 以及[0°, 45°)
     *
     * @param x1 起始横坐标
     * @param y1 起始纵坐标
     * @param x2 终止横坐标
     * @param y2 终止纵坐标
     * @return 方向值
     */
    public static int getDirection(float x1, float y1, float x2, float y2) {
        // gradient does not exist
        if(x1 == x2){
            if(y1 == y2){
                return Direction.ARBITRARY;
            }else {
                return y2 - y1 > 0 ? Direction.DOWN : Direction.UP;
            }
        }
        // gradient exist
        float gradient = (y2 - y1) / (x2 - x1);
        if(gradient >= 1 || gradient <= -1){
            if(y2 > y1){
                return Direction.DOWN;
            }else {
                return Direction.UP;
            }
        }else{
            if(x2 > x1){
                return Direction.RIGHT;
            }else{
                return Direction.LEFT;
            }
        }
    }
}
