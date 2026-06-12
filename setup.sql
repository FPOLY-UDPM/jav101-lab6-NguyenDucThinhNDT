-- Create database EmployeeDB
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'EmployeeDB')
BEGIN
    CREATE DATABASE EmployeeDB;
END
GO

USE EmployeeDB;
GO

SET NOCOUNT ON;

-- Create Departments table if missing
IF OBJECT_ID(N'dbo.Departments', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Departments (
        Id CHAR(3) NOT NULL, -- Mã phòng
        Name NVARCHAR(250) NOT NULL, -- Tên phòng
        Description NVARCHAR(250) NULL, -- Mô tả phòng
        CONSTRAINT PK_Departments PRIMARY KEY (Id)
    );
END

-- Seed Departments (only if empty)
IF NOT EXISTS (SELECT 1 FROM dbo.Departments)
BEGIN
    INSERT INTO dbo.Departments (Id, Name, Description) VALUES
    (N'BGD', N'Ban giám đốc', NULL),
    (N'IT ', N'Phòng IT', N'Phòng IT'),
    (N'KD ', N'Phòng Kinh doanh', NULL),
    (N'KT ', N'Phòng Kế toán', NULL),
    (N'NS ', N'Phòng Nhân sự', NULL);
END

-- Create Employees table if missing
IF OBJECT_ID(N'dbo.Employees', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Employees (
        Id VARCHAR(20) NOT NULL,
        [Password] VARCHAR(50) NOT NULL,
        Fullname NVARCHAR(250) NOT NULL,
        Photo VARCHAR(250) NOT NULL,
        Gender BIT NOT NULL,
        Birthday DATE NOT NULL,
        Salary FLOAT NOT NULL,
        DepartmentId CHAR(3) NOT NULL,
        CONSTRAINT PK_Employees PRIMARY KEY (Id)
    );
END

-- Seed Employees (only if empty)
IF NOT EXISTS (SELECT 1 FROM dbo.Employees)
BEGIN
    INSERT INTO dbo.Employees (Id, [Password], Fullname, Photo, Gender, Birthday, Salary, DepartmentId) VALUES
    (N'NV01', N'123', N'Nguyễn Văn A', N'photo A', 1, CAST(N'2000-01-01' AS DATE), 100, N'BGD'),
    (N'NV02', N'123', N'Trần Thị B', N'photo B', 0, CAST(N'2000-10-10' AS DATE), 110, N'KT '),
    (N'NV03', N'123', N'Phạm Anh C', N'photo C', 1, CAST(N'2001-09-09' AS DATE), 100, N'KD '),
    (N'NV04', N'123', N'Hoàng Minh D', N'photo D', 1, CAST(N'2001-05-05' AS DATE), 105, N'IT ');
END

-- Add foreign key FK_Employees_Departments if both tables exist and FK not present
IF OBJECT_ID(N'dbo.Employees', N'U') IS NOT NULL
   AND OBJECT_ID(N'dbo.Departments', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM sys.foreign_keys fk
        WHERE fk.parent_object_id = OBJECT_ID(N'dbo.Employees')
          AND fk.referenced_object_id = OBJECT_ID(N'dbo.Departments')
    )
    BEGIN
        ALTER TABLE dbo.Employees
        ADD CONSTRAINT FK_Employees_Departments FOREIGN KEY (DepartmentId)
        REFERENCES dbo.Departments(Id)
        ON UPDATE CASCADE
        ON DELETE CASCADE;
    END
END

-- Create Department stored procedures
GO
CREATE OR ALTER PROCEDURE dbo.spSelectAll AS
BEGIN
    SET NOCOUNT ON;
    SELECT Id, Name, Description FROM dbo.Departments;
END
GO

CREATE OR ALTER PROCEDURE dbo.spSelectById @Id VARCHAR(20) AS
BEGIN
    SET NOCOUNT ON;
    SELECT Id, Name, Description FROM dbo.Departments WHERE Id = @Id;
END
GO

CREATE OR ALTER PROCEDURE dbo.spInsert
 @Id VARCHAR(20), @Name NVARCHAR(50), @Description NVARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;
    INSERT INTO dbo.Departments (Id, Name, Description) VALUES (@Id, @Name, @Description);
END
GO

CREATE OR ALTER PROCEDURE dbo.spUpdate
    @Id VARCHAR(20), @Name NVARCHAR(50), @Description NVARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE dbo.Departments SET Name = @Name, Description = @Description WHERE Id = @Id;
END
GO

CREATE OR ALTER PROCEDURE dbo.spDeleteById @Id VARCHAR(20) AS
BEGIN
    SET NOCOUNT ON;
    DELETE FROM dbo.Departments WHERE Id = @Id;
END
GO

-- Verification
SELECT Id, Name, Description FROM dbo.Departments ORDER BY Id;
SELECT Id, Fullname, DepartmentId FROM dbo.Employees ORDER BY Id;
EXEC spSelectById 'IT';
