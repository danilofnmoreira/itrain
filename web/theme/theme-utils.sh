#!/bin/bash

BASEDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

SCSS_ORIGIN="theme/scss/main.scss"
SCSS_DESTINY="src/main/resources/static/css/main.css"

run_node() {
    docker run -it \
               --rm \
               -v $1:/usr/src/app \
               -w /usr/src/app \
               node ${@:2}
}

case $1 in
    watch)
        run_node $BASEDIR/.. npx sass --watch $SCSS_ORIGIN $SCSS_DESTINY --style compressed
        ;;
    compile)
        run_node $BASEDIR/.. npx sass $SCSS_ORIGIN $SCSS_DESTINY --style compressed
        ;;
    *)
        echo "Valid options are:\nwatch\ncompile"
        run_node $BASEDIR $@
        ;;
esac
