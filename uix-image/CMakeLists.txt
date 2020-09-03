
# 设置要求的 CMake 的最低版本
cmake_minimum_required(VERSION 3.4.1)

# 指定头文件的目录
include_directories(opencv/jni/include
        src/main/cpp/include)

#add_library(opencv_imgproc STATIC IMPORTED)
#add_library(opencv_core STATIC IMPORTED)

add_library(opencv_calib3d STATIC IMPORTED)
add_library(opencv_core STATIC IMPORTED)
add_library(opencv_features2d STATIC IMPORTED)
add_library(opencv_flann STATIC IMPORTED)
add_library(opencv_imgcodecs STATIC IMPORTED)
add_library(opencv_imgproc STATIC IMPORTED)
add_library(opencv_stitching STATIC IMPORTED)
add_library(opencv_ximgproc STATIC IMPORTED)

add_library(cpufeatures STATIC IMPORTED)
add_library(IlmImf STATIC IMPORTED)
add_library(libjasper STATIC IMPORTED)
add_library(libjpeg STATIC IMPORTED)
add_library(libpng STATIC IMPORTED)
#add_library(libprotobuf STATIC IMPORTED)
add_library(libtiff STATIC IMPORTED)
add_library(libwebp STATIC IMPORTED)
add_library(tbb STATIC IMPORTED)
add_library(tegra_hal STATIC IMPORTED)

#if(EXISTS ${PROJECT_SOURCE_DIR}/opencv/libs/${ANDROID_ABI}/libtbb.a)
#    add_library(tbb STATIC IMPORTED)
#endif()

# OpenCV 基础库
set_target_properties(opencv_calib3d PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/libs/${ANDROID_ABI}/libopencv_calib3d.a)
set_target_properties(opencv_core PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/libs/${ANDROID_ABI}/libopencv_core.a)
set_target_properties(opencv_features2d PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/libs/${ANDROID_ABI}/libopencv_features2d.a)
set_target_properties(opencv_flann PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/libs/${ANDROID_ABI}/libopencv_flann.a)
set_target_properties(opencv_imgcodecs PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/libs/${ANDROID_ABI}/libopencv_imgcodecs.a)
set_target_properties(opencv_imgproc PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/libs/${ANDROID_ABI}/libopencv_imgproc.a)
set_target_properties(opencv_stitching PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/libs/${ANDROID_ABI}/libopencv_stitching.a)
set_target_properties(opencv_ximgproc PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/libs/${ANDROID_ABI}/libopencv_ximgproc.a)

# OpenCV 拓展库
set_target_properties(cpufeatures PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/3rdparty/libs/${ANDROID_ABI}/libcpufeatures.a)
set_target_properties(IlmImf PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/3rdparty/libs/${ANDROID_ABI}/libIlmImf.a)
set_target_properties(libjasper PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/3rdparty/libs/${ANDROID_ABI}/liblibjasper.a)
set_target_properties(libjpeg PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/3rdparty/libs/${ANDROID_ABI}/liblibjpeg.a)
set_target_properties(libpng PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/3rdparty/libs/${ANDROID_ABI}/liblibpng.a)
#set_target_properties(libprotobuf PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/3rdparty/libs/${ANDROID_ABI}/liblibprotobuf.a)
set_target_properties(libtiff PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/3rdparty/libs/${ANDROID_ABI}/liblibtiff.a)
set_target_properties(libwebp PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/3rdparty/libs/${ANDROID_ABI}/liblibwebp.a)
set_target_properties(tbb PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/3rdparty/libs/${ANDROID_ABI}/libtbb.a)
set_target_properties(tegra_hal PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/3rdparty/libs/${ANDROID_ABI}/libtegra_hal.a)

#set_target_properties(opencv_imgproc PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/libs/${ANDROID_ABI}/libopencv_imgproc.a)
#set_target_properties(opencv_core PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/libs/${ANDROID_ABI}/libopencv_core.a)
#if(EXISTS ${PROJECT_SOURCE_DIR}/opencv/libs/${ANDROID_ABI}/libtbb.a)
#    set_target_properties(tbb PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/opencv/libs/${ANDROID_ABI}/libtbb.a)
#endif()

add_library(img_cropper
        SHARED
        src/main/cpp/img_proc.cpp
        src/main/cpp/img_cropper.cpp
        src/main/cpp/android_utils.cpp)

find_library(log-lib
        log)

find_library(jnigraphics-lib
        jnigraphics)

if(EXISTS ${PROJECT_SOURCE_DIR}/opencv/libs/${ANDROID_ABI}/libtbb.a)
    target_link_libraries(
            img_cropper
            opencv_stitching
            opencv_features2d
            opencv_flann
            opencv_imgcodecs
            opencv_imgproc
            opencv_ximgproc
            opencv_core
            opencv_calib3d

            IlmImf
            cpufeatures
            libjasper
            libjpeg
            libpng
            #libprotobuf
            libtiff
            libwebp
            tbb
            tegra_hal

            ${log-lib}
            ${jnigraphics-lib})
else()
    target_link_libraries(
            img_cropper
            opencv_stitching
            opencv_features2d
            opencv_flann
            opencv_imgcodecs
            opencv_imgproc
            opencv_ximgproc
            opencv_core
            opencv_calib3d

            IlmImf
            cpufeatures
            libjasper
            libjpeg
            libpng
            #libprotobuf
            libtiff
            libwebp
            tbb
            tegra_hal

            ${log-lib}
            ${jnigraphics-lib})
endif()