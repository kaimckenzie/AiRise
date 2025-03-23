using AiRise.Models;
using Microsoft.Extensions.Options;
using MongoDB.Driver;
using MongoDB.Bson;

namespace AiRise.Services;

public class UserService 
{
    private readonly IMongoCollection<User> _userCollection;
    
    public UserService(MongoDBService mongoDBService) 
    {
        _userCollection = mongoDBService.GetCollection<User>("users"); // Use the Users collection
    }

    public async Task CreateAsync(User user)
    {
        await _userCollection.InsertOneAsync(user);
        return;
    }

    public async Task<List<User>> GetAsync()
    {
        return await _userCollection.Find(new BsonDocument()).ToListAsync();
    }

    public async Task UpdateFirebaseUidAsync(string id, string firebaseUid)
    {
        var filter = Builders<User>.Filter.Eq(u => u.Id, id);
        var update = Builders<User>.Update.Set(u => u.FirebaseUid, firebaseUid);
        await _userCollection.UpdateOneAsync(filter, update);
    }

    public async Task DeleteAsync(string id)
    {
        FilterDefinition<User> filter = Builders<User>.Filter.Eq("Id", id);
        await _userCollection.DeleteOneAsync(filter);
        return;
    }

    public async Task<User?> GetUserByFirebaseUidAsync(string firebaseUid)
    {
        return await _userCollection.Find(u => u.FirebaseUid == firebaseUid).FirstOrDefaultAsync();
    }


    public async Task<User?> GetUserByIdAsync(string id)
    {
        return await _userCollection.Find(u => u.Id == id).FirstOrDefaultAsync();
    }
}
