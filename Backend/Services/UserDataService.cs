using AiRise.Models.User;
using MongoDB.Driver;
using MongoDB.Bson;

namespace AiRise.Services
{

    public class UserDataService
    {
        private readonly IMongoCollection<User> _userCollection;
        private readonly IMongoCollection<UserData> _userDataCollection;


        public UserDataService(MongoDBService mongoDBService)
        {
            _userCollection = mongoDBService.GetCollection<User>("users"); // Use the Users collection
            _userDataCollection = mongoDBService.GetCollection<UserData>("user.data");
        }

        public async Task<string> CreateAsync(string firebaseUid)
        {
            var userData = new UserData();
            userData.FirebaseUid = firebaseUid;
            await _userDataCollection.InsertOneAsync(userData);
            return userData.Id;
        }

        public async Task CreateAsync(UserData userData)
        {
            await _userDataCollection.InsertOneAsync(userData);
            return;
        }

        public async Task<UserData?> GetUserData(string firebaseUid)
        {
            return await _userDataCollection.Find(u => u.FirebaseUid == firebaseUid).FirstOrDefaultAsync();
        }

        public async Task<bool> UpdateUserDataAsync(string firebaseUid, UserData updatedData)
        {
            var filter = Builders<UserData>.Filter.Eq(u => u.FirebaseUid, firebaseUid);
            var update = Builders<UserData>.Update
                .Set(u => u.FirstName, updatedData.FirstName)
                .Set(u => u.LastName, updatedData.LastName)
                .Set(u => u.MiddleName, updatedData.MiddleName)
                .Set(u => u.WorkoutGoal, updatedData.WorkoutGoal)
                .Set(u => u.FitnessLevel, updatedData.FitnessLevel)
                .Set(u => u.WorkoutLength, updatedData.WorkoutLength)
                .Set(u => u.WorkoutEquipment, updatedData.WorkoutEquipment)
                .Set(u => u.WorkoutDays, updatedData.WorkoutDays)
                .Set(u => u.WorkoutTime, updatedData.WorkoutTime)
                .Set(u => u.DietaryGoal, updatedData.DietaryGoal)
                .Set(u => u.WorkoutRestrictions, updatedData.WorkoutRestrictions)
                .Set(u => u.HeightMetric, updatedData.HeightMetric)
                .Set(u => u.HeightValue, updatedData.HeightValue)
                .Set(u => u.WeightMetric, updatedData.WeightMetric)
                .Set(u => u.WeightValue, updatedData.WeightValue)
                .Set(u => u.DobDay, updatedData.DobDay)
                .Set(u => u.DobMonth, updatedData.DobMonth)
                .Set(u => u.DobYear, updatedData.DobYear)
                .Set(u => u.ActivityLevel, updatedData.ActivityLevel);

            var result = await _userDataCollection.UpdateOneAsync(filter, update);
            return result.ModifiedCount > 0;
        }

        // Only updates the user name
        public async Task<bool> UpdateUserNameAsync(string firebaseUid, UserData updatedData)
        {
            var filter = Builders<UserData>.Filter.Eq(u => u.FirebaseUid, firebaseUid);
            var update = Builders<UserData>.Update
                .Set(u => u.FirstName, updatedData.FirstName)
                .Set(u => u.LastName, updatedData.LastName)
                .Set(u => u.MiddleName, updatedData.MiddleName);

            var result = await _userDataCollection.UpdateOneAsync(filter, update);
            return result.ModifiedCount > 0;
        }
        
        // Only updates user birthday
        public async Task<bool> UpdateUserDOBAsync(string firebaseUid, UserData updatedData)
        {
            var filter = Builders<UserData>.Filter.Eq(u => u.FirebaseUid, firebaseUid);
            var update = Builders<UserData>.Update
                .Set(u => u.DobDay, updatedData.DobDay)
                .Set(u => u.DobMonth, updatedData.DobMonth)
                .Set(u => u.DobYear, updatedData.DobYear);

            var result = await _userDataCollection.UpdateOneAsync(filter, update);
            return result.ModifiedCount > 0;
        }

        // Only updates the user height
        public async Task<bool> UpdateUserHeightAsync(string firebaseUid, UserData updatedData)
        {
            var filter = Builders<UserData>.Filter.Eq(u => u.FirebaseUid, firebaseUid);
            var update = Builders<UserData>.Update
                .Set(u => u.HeightMetric, updatedData.HeightMetric)
                .Set(u => u.HeightValue, updatedData.HeightValue);

            var result = await _userDataCollection.UpdateOneAsync(filter, update);
            return result.ModifiedCount > 0;
        }

        // Only updates the user weight
        public async Task<bool> UpdateUserWeightAsync(string firebaseUid, UserData updatedData)
        {
            var filter = Builders<UserData>.Filter.Eq(u => u.FirebaseUid, firebaseUid);
            var update = Builders<UserData>.Update
                .Set(u => u.WeightMetric, updatedData.WeightMetric)
                .Set(u => u.WeightValue, updatedData.WeightValue);

            var result = await _userDataCollection.UpdateOneAsync(filter, update);
            return result.ModifiedCount > 0;
        }
    }
}