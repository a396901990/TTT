package com.dean.mapapi.overlayutil;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.dean.greendao.Geocode;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.ui.chart.PointManager;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanGuo on 4/15/16.
 */
public class LineOverlay extends OverlayManager{

    private List<LatLng> mPoints;

    private List<Geocode> mGeocodes;

    public LineOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }

    @Override
    public List<OverlayOptions> getOverlayOptions() {
        List<OverlayOptions> overlayOptionses = new ArrayList<>();

        // start
        overlayOptionses.add((new MarkerOptions())
                .position(mPoints.get(0)).title(START_MARKER)
                .icon(getStartMarker() != null ? getStartMarker() :
                        BitmapDescriptorFactory
                                .fromAssetWithDpi("Icon_start.png")).zIndex(10));

        // end
        overlayOptionses
                .add((new MarkerOptions())
                        .position(mPoints.get(mPoints.size() - 1)).title(END_MARKER)
                        .icon(getTerminalMarker() != null ? getTerminalMarker() :
                                BitmapDescriptorFactory
                                        .fromAssetWithDpi("Icon_end.png"))
                        .zIndex(10));

        // line
        if (mPoints != null) {
            List<Integer> colors = new ArrayList<>();
            colors.add(Integer.valueOf(TTTApplication.getMyColor(R.color.dark_blue)));

            OverlayOptions ooPolyline = new PolylineOptions().width(6)
                    .colorsValues(colors).points(mPoints);
            overlayOptionses.add(ooPolyline);
        }

        // point
        for (int i = 0; i < mGeocodes.size(); i++) {
            Geocode passByGeocode = mGeocodes.get(i);
            if (passByGeocode.getTypes().equals(PointManager.MOUNTAIN)) {
                LatLng passByLL = TTTApplication.getDbHelper().getLatLngWithGeocode(passByGeocode);
//
//                overlayOptionses
//                        .add((new MarkerOptions())
//                                .position(passByLL).title(END_MARKER)
//                                .icon(getMountainMarker())
//                                .zIndex(10));

                OverlayOptions textOption = new TextOptions()
                        .bgColor(Integer.valueOf(TTTApplication.getMyColor(R.color.brown)))
                                .fontSize(28)
                                .fontColor(Integer.valueOf(TTTApplication.getMyColor(R.color.white)))
                                .text(passByGeocode.getName())
                                .rotate(0)
                                .position(passByLL);
                overlayOptionses.add(textOption);
            }
        }

        return overlayOptionses;
    }

    /**
     * 覆写此方法以改变默认起点图标
     */
    public BitmapDescriptor getStartMarker() {
        return null;
    }

    /**
     * 覆写此方法以改变默认绘制颜色
     */
    public int getLineColor() {
        return TTTApplication.getMyColor(R.color.dark_blue);
    }

    /**
     * 覆写此方法以改变默认终点图标
     */
    public BitmapDescriptor getTerminalMarker() {
        return null;
    }

    /**
     * 覆写此方法以改变默认终点图标
     */
    public BitmapDescriptor getMountainMarker() {
        return BitmapDescriptorFactory.fromResource(R.drawable.icon_attention);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public boolean onPolylineClick(Polyline polyline) {
        return false;
    }

    public void setData(List<Geocode> geocodes) {
        this.mGeocodes = geocodes;

        this.mPoints = new ArrayList<LatLng>();

        for (int i = 0; i < mGeocodes.size(); i++) {
            Geocode passByGeocode = mGeocodes.get(i);
            LatLng passByLL = TTTApplication.getDbHelper().getLatLngWithGeocode(passByGeocode);
            mPoints.add(passByLL);
        }
    }
}
