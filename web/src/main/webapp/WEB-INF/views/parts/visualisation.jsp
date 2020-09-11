<%--@elvariable id="options" type="cz.iwitrag.procdungenweb.model.DrawOptions"--%>
<%--@elvariable id="dungeon" type="cz.iwitrag.procdungen.api.data.Dungeon"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
    var windowWidth = document.body.scrollWidth;
    var windowHeight = document.body.scrollHeight - 56;
    console.log(windowHeight);

    var dungeonGridWidth = 0 + ${dungeon.grid.width};
    var dungeonGridHeight = 0 + ${dungeon.grid.height};

    var gridCellSize = parseInt(${options.resolution});
    if (isNaN(gridCellSize))
        gridCellSize = 2;
    var drawRoomNames = ${options.drawNames};
    var drawConnections = ${options.drawConnections};
    var drawCorridors = ${options.drawCorridors};
    var randomColors = ${options.randomColors};
    
    // init HTML canvas and PIXI
    var canvasView = document.createElement("canvas");
    canvasView.id = "canvas";
    var app = new PIXI.Application({
        view: canvasView,
        width: windowWidth,
        height: windowHeight,
        antialias: true,
        backgroundColor: 0x888888
    });
    document.body.appendChild(app.view);
    app.stage.interactive = true;

    // create viewport
    var viewport = new Viewport.Viewport({
        screenWidth: windowWidth,
        screenHeight: windowHeight,
        worldWidth: Math.max(dungeonGridWidth * gridCellSize, windowWidth),
        worldHeight: Math.max(dungeonGridHeight * gridCellSize, windowHeight),
        disableOnContextMenu: true,
        interaction: app.renderer.plugins.interaction
    });
    app.stage.addChild(viewport);
    viewport
        .drag()
        .pinch()
        .wheel({ smooth: 10 })
        .decelerate()
        .clampZoom({ minWidth: viewport.worldWidth/20, maxWidth: viewport.worldWidth , minHeight: viewport.worldHeight/20, maxHeight: viewport.worldHeight })
        //.clamp({left: -viewport.worldWidth/5, right: viewport.worldWidth+(viewport.worldWidth/5), top: -viewport.worldHeight/5, bottom: viewport.worldHeight+(viewport.worldHeight/5)})
        .bounce();
    //.on('clicked', onPointerDown);
    viewport.fitWorld();

    // Create canvas with actual dungeon, if smaller than viewport, it will be centered
    var windowCenterX = viewport.screenWidth / 2.00;
    var windowCenterY = viewport.screenHeight / 2.00;
    var dungeonWidth = dungeonGridWidth * gridCellSize;
    var dungeonHeight = dungeonGridHeight * gridCellSize;
    var canvas = new PIXI.Graphics();
    canvas.x = dungeonWidth >= viewport.screenWidth ? 0 : windowCenterX - (dungeonWidth / 2.00);
    canvas.y = dungeonHeight >= viewport.screenHeight ? 0 : windowCenterY - (dungeonHeight / 2.00);
    canvas.beginFill(0xFFFFFF);
    canvas.drawRect(0, 0, dungeonWidth, dungeonHeight);
    canvas.endFill();
    viewport.addChild(canvas);

    // Draw rooms and their names
    var rectNumber = 0;
    var rectGroups = [];
    var rectGroupIndex = 0;
    rectGroups[0] = new PIXI.Graphics();
    var roomNameNumber = 0;
    var roomNameGroups = [];
    var roomNameGroupIndex = 0;
    roomNameGroups[0] = new PIXI.Graphics();
    <c:forEach var="room" items="${dungeon.rooms}">

    // Draw room shape
    var fillColor = randomColors ? Math.floor(Math.random() * 0xFFFFFF) : 0x555555;
    var fillTransparency = 0.5;
    rectGroups[rectGroupIndex].beginFill(fillColor, fillTransparency);
    <c:forEach var="cell" items="${room.shape.nonEmptyCells}">
    rectGroups[rectGroupIndex].drawRect((${room.position.x + cell.position.x}) * gridCellSize, (${room.position.y + cell.position.y}) * gridCellSize, gridCellSize, gridCellSize);
    rectNumber++;
    if (rectNumber === 500) {
        rectGroups[rectGroupIndex].endFill();
        rectGroupIndex++;
        rectGroups[rectGroupIndex] = new PIXI.Graphics();
        rectGroups[rectGroupIndex].beginFill(fillColor, fillTransparency);
        rectNumber = 0;
    }
    </c:forEach>
    rectGroups[rectGroupIndex].endFill();

    // Draw room name
    if (drawRoomNames) {
        roomNameNumber++;
        if (roomNameNumber === 500) {
            roomNameGroupIndex++;
            roomNameGroups[roomNameGroupIndex] = new PIXI.Graphics();
            roomNameNumber = 0;
        }
        var roomNameText = new PIXI.Text("${room.name}${room.id}");
        var roomNameTextColor = getContrastColor(fillColor.toString(16));
        roomNameText.style.fill = roomNameTextColor;
        roomNameText.style.fontFamily = "Arial";
        roomNameText.style.fontSize = "12";
        roomNameText.style.dropShadow = true;
        roomNameText.style.dropShadowColor = getContrastColor(roomNameTextColor);
        roomNameText.resolution = 4;
        roomNameText.width = (${room.shape.width}) * gridCellSize;
        roomNameText.scale.y = roomNameText.scale.x;
        roomNameText.anchor.x = roomNameText.anchor.y = 0.45; // Align center and middle
        roomNameText.x = (${room.position.x + (room.shape.width/2.00)}) * gridCellSize;
        roomNameText.y = (${room.position.y + (room.shape.height/2.00)}) * gridCellSize;
        roomNameGroups[roomNameGroupIndex].addChild(roomNameText);
    }
    </c:forEach>

    // Draw corridors
    <c:forEach var="corridor" items="${dungeon.corridors}">
    if (drawCorridors) {
        // Draw corridor shape
        fillColor = 0x000055;
        fillTransparency = 0.5;
        rectGroups[rectGroupIndex].beginFill(fillColor, fillTransparency);
        <c:forEach var="cell" items="${corridor.shape.nonEmptyCells}">
        rectGroups[rectGroupIndex].drawRect((${corridor.position.x + cell.position.x}) * gridCellSize, (${corridor.position.y + cell.position.y}) * gridCellSize, gridCellSize, gridCellSize);
        rectNumber++;
        if (rectNumber === 500) {
            rectGroups[rectGroupIndex].endFill();
            rectGroupIndex++;
            rectGroups[rectGroupIndex] = new PIXI.Graphics();
            rectGroups[rectGroupIndex].beginFill(fillColor, fillTransparency);
            rectNumber = 0;
        }
        </c:forEach>
        rectGroups[rectGroupIndex].endFill();
    }
    </c:forEach>

    // Add rooms and corridors to canvas, shapes first, names on top
    for (var i = 0; i < rectGroups.length; i++)
        canvas.addChild(rectGroups[i]);
    for (i = 0; i < roomNameGroups.length; i++)
        canvas.addChild(roomNameGroups[i]);

    if (drawConnections) {
        // Prepare connections between rooms (pairs of rooms without duplicates)
        var linePoints = [];
        var key;
        <c:forEach var="room1" items="${dungeon.rooms}">
        <c:forEach var="room2" items="${room1.connectedRooms}">
        key = Math.min(${room1.id}, ${room2.id}) + "+" + Math.max(${room1.id}, ${room2.id});
        linePoints[key] = [];
        linePoints[key][0] = {x: (${room1.center.x}) * gridCellSize, y: (${room1.center.y}) * gridCellSize};
        linePoints[key][1] = {x: (${room2.center.x}) * gridCellSize, y: (${room2.center.y}) * gridCellSize};
        </c:forEach>
        </c:forEach>

        // Draw connections between rooms
        var roomLines = [];
        var roomLineNumber = -1;
        var roomLineIndex = -1;
        for (key in linePoints) {
            roomLineNumber++;
            if (roomLineNumber % 500 === 0) {
                roomLineIndex++;
                roomLines[roomLineIndex] = new PIXI.Graphics();
                canvas.addChild(roomLines[roomLineIndex]);
            }
            roomLines[roomLineIndex].lineStyle(1.5, 0x000000, 0.5);
            roomLines[roomLineIndex].moveTo(linePoints[key][0].x, linePoints[key][0].y);
            roomLines[roomLineIndex].lineTo(linePoints[key][1].x, linePoints[key][1].y);
        }
    }

    // Draw mouse coordinates and seed
    var coordsStyle = new PIXI.TextStyle({
        fontFamily: "Arial",
        fontSize: 25,
        fill: "white",
        stroke: "black",
        strokeThickness: 1,
        dropShadow: true,
        dropShadowColor: "black",
        dropShadowBlur: 1,
        dropShadowAngle: Math.PI / 6,
        dropShadowDistance: 2
    });
    var coords = new PIXI.Text("X: ---, Y: ---, seed: ---", coordsStyle);
    coords.x = 10;
    coords.y = windowHeight-10;
    coords.anchor.y = 1.0; // Align bottom
    app.stage.addChild(coords);

    // Refresh mouse coordinates
    app.ticker.add(function() {
        // Mouse coordinates on screen, [0,0] is top left corner of canvas
        var mouseGlobalX = app.renderer.plugins.interaction.mouse.global.x;
        var mouseGlobalY = app.renderer.plugins.interaction.mouse.global.y;
        // Mouse coordinates on app window, limited from [0,0] to [windowWidth, windowHeight]
        var mouseX = Math.min(Math.max(mouseGlobalX, 0), windowWidth);
        var mouseY = Math.min(Math.max(mouseGlobalY, 0), windowHeight);
        // Mouse coordinates on viewport world
        var mouseWorldX = viewport.left + (mouseX/viewport.scaled);
        var mouseWorldY = viewport.top + (mouseY/viewport.scaled);
        // Mouse coordinates on viewport world, where [0,0] is top left corner of canvas, which is not always top left of app window
        var mouseCanvasX = mouseWorldX - canvas.x;
        var mouseCanvasY = mouseWorldY - canvas.y;
        // Mouse coordinates in dungeon scale
        var mouseDungeonX = mouseCanvasX / gridCellSize;
        var mouseDungeonY = mouseCanvasY / gridCellSize;
        // Mouse coordinates formatted
        var mouseFinalX = mouseDungeonX.toFixed(2);
        var mouseFinalY = mouseDungeonY.toFixed(2);
        // Hide coords if mouse is outside canvas HTML element
        if (mouseGlobalX < 0 || mouseGlobalY < 0 || mouseGlobalX > windowWidth || mouseGlobalY > windowHeight) {
            mouseFinalX = "---";
            mouseFinalY = "---"
        }
        // Hide coords if mouse is outside dungeon canvas (inside viewport)
        if (mouseDungeonX < 0 || mouseDungeonY < 0 || mouseDungeonX > ${dungeon.grid.width} || mouseDungeonY > ${dungeon.grid.height}) {
            mouseFinalX = "---";
            mouseFinalY = "---"
        }

        coords.text = "X: " + mouseFinalX + ", Y: " + mouseFinalY + ", seed: " + "${dungeon.seed}";
    });

    function getContrastColor(color) {
        color = color.toString();
        if (color.startsWith("#"))
            color = color.slice(1);
        if (color.startsWith("0x"))
            color = color.slice(2);
        var r = parseInt(color.slice(0, 2), 16);
        var g = parseInt(color.slice(2, 4), 16);
        var b = parseInt(color.slice(4, 6), 16);
        return (r * 0.350 + g * 0.450 + b * 0.250) > 165 ? '#000000' : '#FFFFFF';
    }

</script>