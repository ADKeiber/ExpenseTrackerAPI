db = connect( 'mongodb://localhost:27017/expensetracker' );

//Inserts  (home and loans)
db.category.insertMany( [
    {
        _id: ObjectId('674ab549c50dd521b3e43d78'),
        name: 'Home',
        _class: 'com.adk.expensetracker.model.Category'
    },
    {
        _id: ObjectId('674b58173d28dd1cdc642e79'),
        name: 'Loans',
        _class: 'com.adk.expensetracker.model.Category'
    }
] );

// Inserts needed roles (ADMIN and USER)
db.role.insertMany( [
    {
        _id: ObjectId('6747673da773f4db732c497e'),
        value: 'ADMIN',
        _class: 'com.adk.expensetracker.model.Role'
    },
    {
        _id: ObjectId('67476778a773f4db732c497f'),
        value: 'USER',
        _class: 'com.adk.expensetracker.model.Role'
    }
] );

// Inserts needed users (Admin and Generic User)
db.user.insertMany( [
    {
        _id: ObjectId('6748299eceea026ca6ef5f0c'),
        email: 'user@gmail.com',
        username: 'user',
        password: '$2a$10$NKJaGNdELo9VAo7jEOvNue3prdHatjfQ3PjW9Wxl/mUSQhxj51oEy',
        roles: [DBRef('role', new ObjectId('67476778a773f4db732c497f'))],
        _class: 'com.adk.expensetracker.model.User'
    },
    {
            _id: ObjectId('67482a4bceea026ca6ef5f0d'),
            email: 'admin@gmail.com',
            username: 'admin',
            password: '$2a$10$O73/9itVWO.T1SISPGW98.HI6BufCuUL7.BVjn2MRuAc5bW1byz4O',
            roles: [DBRef('role', new ObjectId('67476778a773f4db732c497f')), DBRef('role', new ObjectId('6747673da773f4db732c497e'))],
            _class: 'com.adk.expensetracker.model.User'
    }
] );

// Inserts needed expense
db.expense.insertMany( [
    {
        _id: ObjectId('67482a4bceea026ca6ef5f0e'),
        shortDescription: 'Bank Transfer1',
        fullDescription: 'Bank Transfer to account ending in 1111',
        amount: 10.5,
        date: '2024-09-11T02:56:43.703+00:00',
        category: DBRef('category', new ObjectId('674ab549c50dd521b3e43d78')),
        user : DBRef('user', new ObjectId('6748299eceea026ca6ef5f0c')),
        _class: 'com.adk.expensetracker.model.User'
    }
] );
