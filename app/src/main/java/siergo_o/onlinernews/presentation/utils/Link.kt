package siergo_o.onlinernews.presentation.utils

fun <String> String.getImageUrl():kotlin.String = this.toString().split("src=\"").last().takeWhile { it != '\"'}