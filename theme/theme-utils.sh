#!/bin/bash

BASEDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

SCSS_ORIGIN="web/src/main/resources/static/scss/main.scss"
SCSS_DESTINY="web/src/main/resources/static/css/main.css"

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
        run_node $BASEDIR $@
        ;;
esac
