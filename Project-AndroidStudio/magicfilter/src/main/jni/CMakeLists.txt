# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.
cmake_minimum_required(VERSION 3.4.1)

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add.library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.

set(src_DIR ${CMAKE_CURRENT_SOURCE_DIR})
message("src_DIR: " ${src_DIR})

# Specifies a path to native header files.
include_directories(${src_DIR}/beautify/
                    ${src_DIR}/bitmap/)

aux_source_directory(${src_DIR} DIR_SRC)
aux_source_directory(${src_DIR}/beautify DIR_BEAUTIFY)
aux_source_directory(${src_DIR}/bitmap DIR_BITMAP)

add_library( # Specifies the name of the library.
             MagicBeautify

             # Sets the library as a shared library.
             SHARED

            # Provides a relative path to your source file(s).
            ${DIR_SRC}
            ${DIR_BEAUTIFY}
            ${DIR_BITMAP}
             )


# copy out the lib binary and remove generated files
#set(distribution_DIR ${CMAKE_CURRENT_SOURCE_DIR}/distribution)
#set_target_properties(MagicBeautify
#                      PROPERTIES
#                      LIBRARY_OUTPUT_DIRECTORY
#                      "${distribution_DIR}/MagicBeautify/lib/${ANDROID_ABI}")

# Links your native library against one or more other native libraries.
target_link_libraries( # Specifies the target library.
                      MagicBeautify
                      # Links the log library to the target library.
                      log
                      android
                      jnigraphics
                      )