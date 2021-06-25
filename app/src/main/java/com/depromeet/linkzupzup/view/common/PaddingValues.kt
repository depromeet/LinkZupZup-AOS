package com.depromeet.linkzupzup.view.common

class PaddingValues {

    var leftPadding: Int = 0
    var topPadding: Int = 0
    var rightPadding: Int = 0
    var bottomPading: Int = 0
    
    constructor()
    constructor(leftPadding: Int = 0, topPadding: Int = 0, rightPadding: Int = 0, bottomPading: Int = 0) {
        this.leftPadding = leftPadding
        this.topPadding = topPadding
        this.rightPadding = rightPadding
        this.bottomPading = bottomPading
    }
    constructor(horizontalPadding: Int = 0, verticalPadding: Int = 0) {
        this.leftPadding = horizontalPadding
        this.rightPadding = horizontalPadding
        this.topPadding = verticalPadding
        this.bottomPading = verticalPadding
    }
    constructor(padding: Int = 0) {
        this.leftPadding = padding
        this.topPadding = padding
        this.rightPadding = padding
        this.bottomPading = padding
    }

    fun setValues(paddings: PaddingValues) {
        this.leftPadding = paddings.leftPadding
        this.topPadding = paddings.topPadding
        this.rightPadding = paddings.rightPadding
        this.bottomPading = paddings.rightPadding
    }
}