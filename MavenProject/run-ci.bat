@echo off
echo ========================================
echo  Starting Maven CI Local Build
echo ========================================

REM 执行 Maven 构建（安静模式，运行测试）
mvn -q -DskipTests=false clean verify

REM 检查 Maven 命令是否执行成功
if %errorlevel% neq 0 (
    echo [ERROR] Maven build failed!
    exit /b %errorlevel%
)

REM 输出测试报告目录
echo.
echo ========================================
echo Test reports are available at:
echo %cd%\target\surefire-reports\
echo ========================================

pause