package com.dean.mapapi.overlayutil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.dean.greendao.Geocode;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.ui.chart.PointManager;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanGuo on 4/15/16.
 */
public class LineOverlay extends OverlayManager{

    public static String START_MARKER = "start_marker";
    public static String END_MARKER = "end_marker";
    public static String MOUNTAIN_MARKER = "mountain_marker";

    public static String GEO_NAME = "geo_name";
    public static String GEO_HEIGHT = "geo_height";
    public static String GEO_MILESTONE = "geo_milestone";

    private List<Geocode> mGeocodes;

    private boolean isShowMore = false;

    public LineOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }

    @Override
    public List<OverlayOptions> getOverlayOptions() {
        List<OverlayOptions> overlayOptionses = new ArrayList<>();

        /** start **/
        Geocode startGeocode = mGeocodes.get(0);
        LatLng startLL = TTTApplication.getDbHelper().getLatLngWithGeocode(startGeocode);

        overlayOptionses.add((new MarkerOptions())
                .extraInfo(getGeoExtraInfo(startGeocode))
                .position(startLL)
                .title(START_MARKER)
                .icon(getStartMarker())
                .zIndex(10));

        /** end **/
        Geocode endGeocode = mGeocodes.get(mGeocodes.size()-1);
        LatLng endLL = TTTApplication.getDbHelper().getLatLngWithGeocode(endGeocode);

        overlayOptionses.add((new MarkerOptions())
                .extraInfo(getGeoExtraInfo(endGeocode))
                .position(endLL)
                .title(END_MARKER)
                .icon(getTerminalMarker())
                .zIndex(10));

        /** line **/
        ArrayList<LatLng> mPoints = new ArrayList<LatLng>();
        // 去掉首尾
        for (int i = 0; i < mGeocodes.size(); i++) {
            Geocode passByGeocode = mGeocodes.get(i);
            LatLng passByLL = TTTApplication.getDbHelper().getLatLngWithGeocode(passByGeocode);
            mPoints.add(passByLL);
        }
        List<Integer> colors = new ArrayList<>();
        colors.add(Integer.valueOf(getLineColor()));

        OverlayOptions ooPolyline = new PolylineOptions()
                .width(6)
                .colorsValues(colors)
                .points(mPoints);

        overlayOptionses.add(ooPolyline);

        /** point，route时候不显示点，因为点太多 **/
        if (isShowMore) {
            for (Geocode geocode : mGeocodes) {

                // MOUNTAIN
                if (geocode.getTypes().equals(PointManager.MOUNTAIN)) {
                    LatLng passByLL = TTTApplication.getDbHelper().getLatLngWithGeocode(geocode);

                    OverlayOptions markOption = new MarkerOptions()
                    .position(passByLL)
                            .title(MOUNTAIN_MARKER)
                            .extraInfo(getGeoExtraInfo(geocode))
                            .icon(getMountainMarker(geocode.getName()))
                            .zIndex(8);

                    overlayOptionses.add(markOption);
                }
            }
        }

        return overlayOptionses;
    }

    public Bundle getGeoExtraInfo(Geocode geocode) {
        Bundle bundle = new Bundle();

        bundle.putString(GEO_NAME, geocode.getName());

        String height = StringUtil.formatDoubleToInteger(geocode.getElevation());
        height = String.format(Constants.GUIDE_OVERALL_HEIGHT_FORMAT, height);
        bundle.putString(GEO_HEIGHT, height);

        String road = geocode.getRoad();
        String milestone = geocode.getMilestone();
        if (!TextUtils.isEmpty(milestone)) {
            if (TextUtils.isEmpty(road)) {
                milestone = String.format(Constants.GUIDE_OVERALL_MILESTONE_FORMAT_NO_ROAD, milestone);
            } else {
                milestone = String.format(Constants.GUIDE_OVERALL_MILESTONE_FORMAT, road, milestone);
            }
        } else {
            milestone = String.format(Constants.GUIDE_OVERALL_ROAD_FORMAT, road);
        }
        bundle.putString(GEO_MILESTONE, milestone);

        return bundle;
    }

    /**
     * 覆写此方法以改变默认起点图标
     */
    public BitmapDescriptor getStartMarker() {
        return BitmapDescriptorFactory.fromAssetWithDpi("Icon_start.png");
    }

    /**
     * 覆写此方法以改变默认终点图标
     */
    public BitmapDescriptor getTerminalMarker() {
        return BitmapDescriptorFactory.fromAssetWithDpi("Icon_end.png");
    }

    /**
     * 覆写此方法以改变默认终点图标
     */
    public BitmapDescriptor getMountainMarker(String name) {
        LayoutInflater layoutInflater = LayoutInflater.from(TTTApplication.getContext());
        ViewGroup showLayout = (ViewGroup) layoutInflater.inflate(R.layout.map_show_mountain_layout, null);

        TextView title = (TextView) showLayout.findViewById(R.id.map_mountain_title);
        title.setText(name);
        return BitmapDescriptorFactory.fromView(showLayout);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    public int getLineColor() {
        return TTTApplication.getMyColor(R.color.dark_blue);
    }

    @Override
    public boolean onPolylineClick(Polyline polyline) {
        return false;
    }

    public void setData(List<Geocode> geocodes) {
        this.mGeocodes = geocodes;
    }

    public void setShowMore(boolean showMore) {
        isShowMore = showMore;
    }
}
